package pl.edu.pw.elka.sag.agents.highwaycode;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import pl.edu.pw.elka.sag.constants.ConversationTypes;
import pl.edu.pw.elka.sag.ontology.predicates.CanPassTrafficLightPredicate;

public class ServeTrafficLightRuleBehaviour extends RuleBehaviourBase
{
	private static final long serialVersionUID = -8705231544834694720L;
	
	public ServeTrafficLightRuleBehaviour(Agent agent)
	{
		super(agent, ConversationTypes.TRAFFIC_LIGHT_RULE_CONVERSATION_TYPE);
	}

	@Override
	protected void fillReply(ACLMessage message, ACLMessage reply) throws UnreadableException
	{
		CanPassTrafficLightPredicate predicate = (CanPassTrafficLightPredicate) message.getContentObject();
		
		if (getHighwayCodeAgent().getHighwayCode().getTrafficLightRule().evaluate(null, predicate.getTrafficLightStatus()))
		{
			reply.setPerformative(ACLMessage.AGREE);
		}
		else
		{
			reply.setPerformative(ACLMessage.REFUSE);
		}
	}
}
