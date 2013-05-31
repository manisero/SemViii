package pl.edu.pw.elka.sag.agents.highwaycode;

import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

import pl.edu.pw.elka.sag.constants.ConversationTypes;
import pl.edu.pw.elka.sag.ontology.predicates.HasPriorityPredicate;

public class ServePriorityRuleBehaviour extends RuleBehaviourBase
{
	protected ServePriorityRuleBehaviour(HighwayCodeAgent agent)
	{
		super(agent, ConversationTypes.PRIORITY_RULE_CONVERSATION_TYPE);
	}

	private static final long serialVersionUID = -6195686586988035082L;

	@Override
	protected void fillReply(ACLMessage message, ACLMessage reply) throws UnreadableException, IOException
	{
		HasPriorityPredicate predicate = (HasPriorityPredicate) message.getContentObject();
		
		if (getHighwayCode().getPriorityRule().evaluate(predicate))
		{
			reply.setPerformative(ACLMessage.AGREE);
		}
		else
		{
			reply.setPerformative(ACLMessage.REFUSE);
		}
	}
}
