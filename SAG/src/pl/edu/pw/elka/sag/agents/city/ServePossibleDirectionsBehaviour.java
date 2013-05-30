package pl.edu.pw.elka.sag.agents.city;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;
import java.util.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.actions.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import pl.edu.pw.elka.sag.ontology.concepts.Location;
import pl.edu.pw.elka.sag.ontology.predicates.*;

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
				CanTurnOnCrossroadsPredicate predicate = (CanTurnOnCrossroadsPredicate) message.getContentObject();
				Location crossroadsLocation = predicate.getCrossroadsLocation();
				Collection<Direction> directions = new GetCrossroadsDirectionsAction().execute(city, crossroadsLocation);
				
				ArrayList<CanTurnOnCrossroadsPredicate> result = new ArrayList<CanTurnOnCrossroadsPredicate>();
				
				for (Direction direction : directions)
				{
					result.add(new CanTurnOnCrossroadsPredicate(direction, crossroadsLocation));
				}
				
				ACLMessage reply = message.createReply();
				reply.setContentObject(result);
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
