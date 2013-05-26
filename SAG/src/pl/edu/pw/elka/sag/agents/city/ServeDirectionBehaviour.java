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
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.POSSIBLE_DIRECTIONS_CONVERSATION_TYPE);
	
	private final int citySize;
	
	public ServeDirectionBehaviour(int citySize)
	{
		this.citySize = citySize;
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
		
		if (location.getX() < citySize - 1)
		{
			directions.add(Direction.EAST);
		}
		
		if (location.getY() > 0)
		{
			directions.add(Direction.SOUTH);
		}
		
		if (location.getY() < citySize - 1)
		{
			directions.add(Direction.NORTH);
		}
		
		return directions;
	}
}
