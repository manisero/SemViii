package pl.edu.pw.elka.sag.agents.highwaycode;

import jade.lang.acl.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.predicates.*;

public class ServeTrafficLightRuleBehaviour extends RuleBehaviourBase
{
	private static final long serialVersionUID = -8705231544834694720L;
	
	public ServeTrafficLightRuleBehaviour(HighwayCodeAgent agent)
	{
		super(agent, ConversationTypes.TRAFFIC_LIGHT_RULE_CONVERSATION_TYPE);
	}

	@Override
	protected void fillReply(ACLMessage message, ACLMessage reply) throws UnreadableException
	{
		CanPassTrafficLightPredicate predicate = (CanPassTrafficLightPredicate) message.getContentObject();
		
		if (getHighwayCode().getTrafficLightRule().evaluate(predicate))
		{
			reply.setPerformative(ACLMessage.AGREE);
		}
		else
		{
			reply.setPerformative(ACLMessage.REFUSE);
		}
	}
}
