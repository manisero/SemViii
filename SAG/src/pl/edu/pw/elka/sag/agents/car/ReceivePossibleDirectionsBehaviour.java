package pl.edu.pw.elka.sag.agents.car;

import java.util.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class ReceivePossibleDirectionsBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -6171122937430472879L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.POSSIBLE_DIRECTIONS_CONVERSATION_TYPE);
	
	public ReceivePossibleDirectionsBehaviour(CarAgent agent)
	{
		super(agent);
	}
	
	private CarAgent getCarAgent()
	{
		return (CarAgent) myAgent;
	}
	
	@Override
	public void action()
	{
		ACLMessage message = myAgent.receive(messageTemplate);
		
		if (message != null)
		{
			try
			{
				DirectionsCollection directions = (DirectionsCollection) message.getContentObject();
				getCarAgent().setNextDirection(chooseDirection(directions));
			}
			catch (UnreadableException e)
			{
				e.printStackTrace();
			}				
		}
		else
		{
			block();
		}
	}
	
	private Direction chooseDirection(DirectionsCollection directions)
	{
		List<Direction> directionsList = directions.getDirections();
		
		Direction currentCarDirection = getCarAgent().getDirection();
		
		if (currentCarDirection != null)
		{
			directionsList.remove(currentCarDirection.getOpposite());
		}
		
		return directionsList.get(new Random().nextInt(directionsList.size()));
	}
}
