package pl.edu.pw.elka.sag.agents.city;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.*;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;

public class ServeDirectionBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -5851725356214090232L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.CAR_DIRECTION_CONVERSATION_TYPE);
	
	private final int cityWidth;
	private final int cityHeight;
	
	public ServeDirectionBehaviour(int cityWidth, int cityHeight)
	{
		this.cityWidth = cityWidth;
		this.cityHeight = cityHeight;
	}

	@Override
	public void action()
	{
		ACLMessage message = myAgent.receive(messageTemplate);
		
		if (message != null)
		{
			try
			{
				Location location = (Location) message.getContentObject();
				List<Direction> directions = getPossibleDirections(location);
				
				ACLMessage reply = message.createReply();
				reply.setContentObject(new DirectionsCollection(directions));
				
				myAgent.send(reply);
			}
			catch (UnreadableException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			block();
		}
	}
	
	private List<Direction> getPossibleDirections(Location location)
	{
		List<Direction> directions = new LinkedList<Direction>();
		
		if (location.getX() > 0)
		{
			directions.add(Direction.WEST);
		}
		
		if (location.getX() < cityWidth - 1)
		{
			directions.add(Direction.EAST);
		}
		
		if (location.getY() > 0)
		{
			directions.add(Direction.SOUTH);
		}
		
		if (location.getY() < cityHeight - 1)
		{
			directions.add(Direction.NORTH);
		}
		
		return directions;
	}
}
