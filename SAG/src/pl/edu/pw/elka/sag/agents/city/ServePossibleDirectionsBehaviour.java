package pl.edu.pw.elka.sag.agents.city;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;
import java.util.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;
import pl.edu.pw.elka.sag.entities.Location;

public class ServePossibleDirectionsBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -5851725356214090232L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.POSSIBLE_DIRECTIONS_CONVERSATION_TYPE);
	
	private final City city;
	
	public ServePossibleDirectionsBehaviour(Agent agent, City city)
	{
		super(agent);
		this.city = city;
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
				List<Direction> directions = city.getPossibleDirections(location);
				
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
}
