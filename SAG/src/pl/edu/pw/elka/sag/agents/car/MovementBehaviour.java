package pl.edu.pw.elka.sag.agents.car;

import java.io.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;
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
		
		if (step < 0)
		{
			System.out.println("Step: " + step);
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
			
			// TODO: check crossroads
			getCarAgent().setStatus(CarStatus.OnCrossroads);
		}
		
		getCarAgent().move();
	}
	
	private void requestPossibleDirections()
	{
		try
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
			
			Location location = new Location(x, y);
			
			System.out.println("CarLoc: " + getCarAgent().getLocation() + ", CarDir: " + getCarAgent().getDirection() + "; sent: " + location);
			
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(getCarAgent().getCityAgentID());
			message.setConversationId(ConversationTypes.POSSIBLE_DIRECTIONS_CONVERSATION_TYPE);
			message.setContentObject(location);
			
			myAgent.send(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
