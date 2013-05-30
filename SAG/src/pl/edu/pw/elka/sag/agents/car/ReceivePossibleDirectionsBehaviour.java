package pl.edu.pw.elka.sag.agents.car;

import java.util.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.actions.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import pl.edu.pw.elka.sag.ontology.predicates.*;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class ReceivePossibleDirectionsBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -6171122937430472879L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.POSSIBLE_DIRECTIONS_CONVERSATION_TYPE);
	
	private final Car car;
	
	public ReceivePossibleDirectionsBehaviour(Agent agent, Car car)
	{
		super(agent);
		this.car = car;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void action()
	{
		ACLMessage message = myAgent.receive(messageTemplate);
		
		if (message != null)
		{
			try
			{
				ArrayList<CanTurnOnCrossroadsPredicate> predicates = (ArrayList<CanTurnOnCrossroadsPredicate>) message.getContentObject();
				List<Direction> directions = new ArrayList<Direction>();
				
				for (CanTurnOnCrossroadsPredicate predicate : predicates)
				{
					directions.add(predicate.getTurnDirection());
				}
				
				Direction direction = new ChooseCarDirectionAction().Ececute(car, directions);
				car.setNextDirection(direction);
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
}
