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
		int position = getCarAgent().getDirection().hasParts(Direction.EAST, Direction.WEST) 
							? getCarAgent().getLocation().getX()
							: getCarAgent().getLocation().getY();
		
		position %= 10;
		
		if (position == 0)
		{
			getCarAgent().setDirection(getCarAgent().getNextDirection());
			getCarAgent().setNextDirection(Direction.UNKNOWN);
		}
		else if (position == 1)
		{
			getCarAgent().setStatus(CarStatus.Driving);
		}
		else if (position == 8)
		{
			getCarAgent().setStatus(CarStatus.NearCrossroads);
			requestPossibleDirections();
		}
		else if (position == 9)
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
			Location location = getCarAgent().getLocation();
			
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
