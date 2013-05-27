package pl.edu.pw.elka.sag.agents.car;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;
import java.util.*;

import pl.edu.pw.elka.sag.agents.trafficlight.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;
import pl.edu.pw.elka.sag.entities.Location;
import pl.edu.pw.elka.sag.util.*;

public class MovementBehaviour extends TickerBehaviour
{
	private static final long serialVersionUID = -5290333473439069526L;
	
	public MovementBehaviour(CarAgent agent, int speed)
	{
		super(agent, speed / 10);
	}

	private CarAgent getCarAgent()
	{
		return (CarAgent) myAgent;
	}
	
	@Override
	protected void onTick()
	{
		int step = getStep();
		
		if (step == 0)
		{
			getCarAgent().setDirection(getCarAgent().getNextDirection());
			getCarAgent().setNextCrossroadsLocation(getNextCrossroadsLocation());
			getCarAgent().setNextDirection(Direction.UNKNOWN);
			getCarAgent().setNextTrafficLight(null);
			getCarAgent().setNextTrafficLightAllowedDirection(null);
			getCarAgent().setOtherCarsToCheck(0);
			getCarAgent().setOtherCarsChecked(0);
			getCarAgent().setHasPriority(true);
		}
		else if (step == 1)
		{
			getCarAgent().setStatus(CarStatus.Driving);
		}
		else if (step == 6)
		{
			requestPossibleDirections();
		}
		else if (step == 7)
		{
			if (getCarAgent().getNextDirection() == Direction.UNKNOWN)
			{
				return;
			}
			
			getCarAgent().setStatus(CarStatus.NearCrossroads);
			
			if (findTrafficLight())
			{
				checkTrafficLight();
			}
			else
			{
				checkOtherCars();
			}
		}
		else if (step == 8)
		{
			if (getCarAgent().getNextTrafficLight() != null)
			{
				if (getCarAgent().getNextDirection() == null)
				{
					return;
				}
				
				if (!getCarAgent().getNextTrafficLightAllowedDirection().hasPart(getCarAgent().getDirection()))
				{
					checkTrafficLight();
					return;
				}
			}
			else if (getCarAgent().getOtherCarsChecked() < getCarAgent().getOtherCarsToCheck())
			{
				return;
			}
			else if (!getCarAgent().getHasPriority())
			{
				getCarAgent().setHasPriority(true);
				checkOtherCars();
				return;
			}
			
			getCarAgent().setStatus(CarStatus.OnCrossroads);
		}
		
		getCarAgent().move();
	}
	
	int getStep()
	{
		int position = getCarAgent().getDirection().hasAnyOfParts(Direction.EAST, Direction.WEST) 
				? getCarAgent().getLocation().getX()
				: getCarAgent().getLocation().getY();
				
		int step = position % 10;
		
		if (step != 0 && getCarAgent().getDirection().hasAnyOfParts(Direction.WEST, Direction.SOUTH))
		{
			step = 10 - step;
		}
		
		return step;
	}
	
	private Location getNextCrossroadsLocation()
	{
		Location carLocation = getCarAgent().getLocation();
		Direction carDirection = getCarAgent().getDirection();
		
		if (carDirection == Direction.NORTH)
		{
			return new Location(carLocation.getX(), carLocation.getY() + 10);
		}
		else if (carDirection == Direction.SOUTH)
		{
			return new Location(carLocation.getX(), carLocation.getY() - 10);
		}
		else if (carDirection == Direction.EAST)
		{
			return new Location(carLocation.getX() + 10, carLocation.getY());
		}
		else if (carDirection == Direction.WEST)
		{
			return new Location(carLocation.getX() - 10, carLocation.getY());
		}
		else
		{
			return null;
		}
	}
	
	private void requestPossibleDirections()
	{
		try
		{
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(getCarAgent().getCityAgentID());
			message.setConversationId(ConversationTypes.POSSIBLE_DIRECTIONS_CONVERSATION_TYPE);
			message.setContentObject(getCarAgent().getNextCrossroadsLocation());
			
			myAgent.send(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean findTrafficLight()
	{
		String serviceName = TrafficLightAgent.getTrafficLightServiceName(getCarAgent().getNextCrossroadsLocation());
		List<AID> trafficLights = AgentRegistrar.getInstance().getAgents(getCarAgent(), TrafficLightAgent.class, serviceName, true);
		
		if (trafficLights.size() > 0)
		{
			getCarAgent().setNextTrafficLight(trafficLights.get(0));
			return true;
		}
		
		return false;
	}
	
	private void checkTrafficLight()
	{
		getCarAgent().setNextTrafficLightAllowedDirection(null);
		
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.addReceiver(getCarAgent().getNextTrafficLight());
		message.setConversationId(ConversationTypes.TRAFFIC_LIGHTS_CONVERSATION_TYPE);
		myAgent.send(message);
	}
	
	private void checkOtherCars()
	{
		List<AID> cars = AgentRegistrar.getInstance().getAgents(getCarAgent(), CarAgent.class);
		
		getCarAgent().setOtherCarsToCheck(cars.size());
		
		if (cars.size() == 0)
		{
			return;
		}
		
		try
		{
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			
			for (AID car : cars)
			{
				message.addReceiver(car);
			}
			
			message.setConversationId(ConversationTypes.CAR_STATUS_INFO_CONVERSATION_TYPE);
			message.setContentObject(getCarAgent().getNextCrossroadsLocation());
			
			myAgent.send(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
