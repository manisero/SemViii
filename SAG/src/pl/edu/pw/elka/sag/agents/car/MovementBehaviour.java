package pl.edu.pw.elka.sag.agents.car;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;
import java.util.*;

import pl.edu.pw.elka.sag.agents.trafficlight.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.actions.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import pl.edu.pw.elka.sag.ontology.predicates.*;
import pl.edu.pw.elka.sag.util.*;

public class MovementBehaviour extends TickerBehaviour
{
	private static final long serialVersionUID = -5290333473439069526L;
	
	private final Car car;
	private final AID cityAgentId;
	
	public MovementBehaviour(Agent agent, Car car, AID cityAgentId)
	{
		super(agent, car.getSpeed() / 10);
		this.car = car;
		this.cityAgentId = cityAgentId;
	}
	
	@Override
	protected void onTick()
	{
		int step = getStep();
		
		if (step == 0)
		{
			car.setDirection(car.getNextDirection());
			car.setNextCrossroadsLocation(new GetNextCrossroadsLocationAction().execute(car.getLocation(), car.getDirection()));
			car.setNextDirection(Direction.UNKNOWN);
			car.setNextTrafficLight(null);
			car.setNextTrafficLightStatus(null);
			car.setOtherCarsToCheck(0);
			car.setOtherCarsChecked(0);
			car.setHasPriority(true);
		}
		else if (step == 1)
		{
			car.setStatus(CarStatus.Driving);
		}
		else if (step == 6)
		{
			requestPossibleDirections();
		}
		else if (step == 7)
		{
			if (car.getNextDirection() == Direction.UNKNOWN)
			{
				return;
			}
			
			car.setStatus(CarStatus.NearCrossroads);
			
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
			if (car.getNextTrafficLight() != null)
			{
				if (car.getNextDirection() == null)
				{
					return;
				}
				
				if (car.getNextTrafficLightStatus() != TrafficLightStatus.GREEN)
				{
					checkTrafficLight();
					return;
				}
			}
			else if (car.getOtherCarsChecked() < car.getOtherCarsToCheck())
			{
				return;
			}
			else if (!car.getHasPriority())
			{
				car.setHasPriority(true);
				checkOtherCars();
				return;
			}
			
			car.setStatus(CarStatus.OnCrossroads);
		}
		
		car.move();
	}
	
	int getStep()
	{
		if (car.getDirection() == null || car.getDirection() == Direction.UNKNOWN)
		{
			return 0;
		}
		
		int position = car.getDirection().hasAnyOfParts(Direction.EAST, Direction.WEST) 
				? car.getLocation().getX()
				: car.getLocation().getY();
				
		int step = position % 10;
		
		if (step != 0 && car.getDirection().hasAnyOfParts(Direction.WEST, Direction.SOUTH))
		{
			step = 10 - step;
		}
		
		return step;
	}
	
	private void requestPossibleDirections()
	{
		try
		{
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(cityAgentId);
			message.setConversationId(ConversationTypes.POSSIBLE_DIRECTIONS_CONVERSATION_TYPE);
			message.setContentObject(new CanTurnOnCrossroadsPredicate(null, car.getNextCrossroadsLocation()));
			
			myAgent.send(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean findTrafficLight()
	{
		String serviceName = TrafficLightAgent.getTrafficLightServiceName(car.getNextCrossroadsLocation());
		List<AID> trafficLights = AgentRegistrar.getInstance().getAgents(myAgent, TrafficLightAgent.class, serviceName, true);
		
		if (trafficLights.size() > 0)
		{
			car.setNextTrafficLight(trafficLights.get(0));
			return true;
		}
		
		return false;
	}
	
	private void checkTrafficLight()
	{
		try
		{
			car.setNextTrafficLightStatus(null);
			
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(car.getNextTrafficLight());
			message.setConversationId(ConversationTypes.TRAFFIC_LIGHT_STATUS_CONVERSATION_TYPE);
			message.setContentObject(car.getDirection());
			
			myAgent.send(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void checkOtherCars()
	{
		List<AID> cars = AgentRegistrar.getInstance().getAgents(myAgent, CarAgent.class);
		
		car.setOtherCarsToCheck(cars.size());
		
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
			message.setContentObject(car.getNextCrossroadsLocation());
			
			myAgent.send(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
