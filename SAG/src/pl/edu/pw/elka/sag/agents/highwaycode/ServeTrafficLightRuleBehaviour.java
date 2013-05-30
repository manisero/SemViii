package pl.edu.pw.elka.sag.agents.highwaycode;

import jade.core.*;
import jade.lang.acl.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.logic.highwaycode.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class ServeTrafficLightRuleBehaviour extends RuleBehaviourBase
{
	private static final long serialVersionUID = -8705231544834694720L;
	
	public ServeTrafficLightRuleBehaviour(Agent agent, IHighwayCode highwayCode)
	{
		super(agent, highwayCode, ConversationTypes.TRAFFIC_LIGHT_RULE_CONVERSATION_TYPE);
	}

	@Override
	protected void fillReply(ACLMessage message, ACLMessage reply) throws UnreadableException
	{
		TrafficLightStatus trafficLightStatus = (TrafficLightStatus) message.getContentObject();
		
		if (highwayCode.getTrafficLightRule().evaluate(null, trafficLightStatus))
		{
			reply.setPerformative(ACLMessage.AGREE);
		}
		else
		{
			reply.setPerformative(ACLMessage.REFUSE);
		}
	}
}
