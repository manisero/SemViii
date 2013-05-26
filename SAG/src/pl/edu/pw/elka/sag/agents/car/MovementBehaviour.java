package pl.edu.pw.elka.sag.agents.car;

import java.io.*;
import java.util.*;

import pl.edu.pw.elka.sag.agents.trafficlight.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;
import pl.edu.pw.elka.sag.entities.Location;
import pl.edu.pw.elka.sag.util.*;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

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
		int position = getCarAgent().getDirection().hasAnyOfParts(Direction.EAST, Direction.WEST) 
							? getCarAgent().getLocation().getX()
							: getCarAgent().getLocation().getY();
							
		int step = position % 10;
		
		if (step != 0 && getCarAgent().getDirection().hasAnyOfParts(Direction.WEST, Direction.SOUTH))
		{
			step = 10 - step;
		}
		
		if (step == 0)
		{
			getCarAgent().setDirection(getCarAgent().getNextDirection());
			getCarAgent().setNextDirection(Direction.UNKNOWN);
		}
		else if (step == 1)
		{
			getCarAgent().setStatus(CarStatus.Driving);
		}
		else if (step == 8)
		{
			getCarAgent().setStatus(CarStatus.NearCrossroads);
			requestPossibleDirections();
		}
		else if (step == 9)
		{
			if (getCarAgent().getNextDirection() == Direction.UNKNOWN)
			{
				return;
			}
			
			if (!checkTrafficLight())
			{
				return;
			}
			
			getCarAgent().setStatus(CarStatus.OnCrossroads);
		}
		
		getCarAgent().move();
	}
	
	private void requestPossibleDirections()
	{
		try
		{
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(getCarAgent().getCityAgentID());
			message.setConversationId(ConversationTypes.POSSIBLE_DIRECTIONS_CONVERSATION_TYPE);
			message.setContentObject(getNextCrossroadsLocation());
			
			myAgent.send(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean checkTrafficLight()
	{
		Location trafficLightLocation = getNextCrossroadsLocation();
		
		List<AID> trafficLights = AgentRegistrar.getInstance().getAgents(getCarAgent(), TrafficLightAgent.class, TrafficLightAgent.getTrafficLightServiceName(trafficLightLocation));
		
		if (trafficLights.size() > 0)
		{
			System.out.println("Traffic light: " + trafficLights.get(0).getLocalName());
			
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(trafficLights.get(0));
			message.setConversationId(ConversationTypes.TRAFFIC_LIGHTS_CONVERSATION_TYPE);
			myAgent.send(message);
			
			MessageTemplate responseTemplate = MessageTemplate.MatchConversationId(ConversationTypes.TRAFFIC_LIGHTS_CONVERSATION_TYPE);
			ACLMessage response = myAgent.blockingReceive(responseTemplate); // TODO: remove blockingReceive usage
			
			try
			{
				Direction allowedDirections = (Direction) response.getContentObject();
				
				System.out.println("Allowed: " + allowedDirections);
				System.out.println("Direction: " + getCarAgent().getDirection());
				
				if (!allowedDirections.hasPart(getCarAgent().getDirection()))
				{
					return false;
				}
			}
			catch (UnreadableException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}
	
	private Location getNextCrossroadsLocation()
	{
		int x = (getCarAgent().getLocation().getX() / 10) * 10;
		int y = (getCarAgent().getLocation().getY() / 10) * 10;
		
		if (getCarAgent().getDirection() == Direction.EAST)
		{
			x += 10;
		}
		else if (getCarAgent().getDirection() == Direction.NORTH)
		{
			y += 10;
		}
		
		return new Location(x, y);
	}
}
