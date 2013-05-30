package pl.edu.pw.elka.sag.agents.car;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;
import java.util.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.logic.actions.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import pl.edu.pw.elka.sag.ontology.predicates.*;
import pl.edu.pw.elka.sag.util.*;

public class MovementBehaviour extends TickerBehaviour
{
	private static final long serialVersionUID = -5290333473439069526L;
	
	private final Car car;
	
	public MovementBehaviour(Agent agent, Car car)
	{
		super(agent, car.getSpeed() / 10);
		this.car = car;
	}
	
	@Override
	protected void onTick()
	{
		int step = getStep();
		
		if (step == 0)
		{
			car.setDirection(car.getNextDirection());
			car.setNextDirection(Direction.UNKNOWN);
			
			car.setNextCrossroadsLocation(new GetNextCrossroadsLocationAction().execute(car.getLocation(), car.getDirection()));
			
			car.setNextTrafficLight(null);
			car.setNextTrafficLightRuleResult(false);
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
			
			AID trafficLightId = AgentSearchUtilities.findTrafficLight(myAgent, car.getNextCrossroadsLocation());
			
			if (trafficLightId != null)
			{
				car.setNextTrafficLight(trafficLightId);
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
				
				if (!car.getNextTrafficLightRuleResult())
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
			message.addReceiver(AgentSearchUtilities.findCityAgent(myAgent));
			message.setConversationId(ConversationTypes.POSSIBLE_DIRECTIONS_CONVERSATION_TYPE);
			message.setContentObject(new CanTurnOnCrossroadsPredicate(null, car.getNextCrossroadsLocation()));
			
			myAgent.send(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void checkTrafficLight()
	{
		try
		{
			car.setNextTrafficLightRuleResult(false);
			
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
		List<AID> cars = AgentRegistry.getInstance().getAgents(myAgent, CarAgent.class);
		
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
