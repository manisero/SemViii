package pl.edu.pw.elka.sag.agents.car;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class ReceiveTrafficLightRuleBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -5550191432247487437L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.TRAFFIC_LIGHT_RULE_CONVERSATION_TYPE);
	
	private final Car car;
	
	public ReceiveTrafficLightRuleBehaviour(Agent agent, Car car)
	{
		super(agent);
		this.car = car;
	}
	
	@Override
	public void action()
	{
		ACLMessage message = myAgent.receive(messageTemplate);
		
		if (message != null)
		{
			car.setNextTrafficLightRuleResult(message.getPerformative() == ACLMessage.AGREE);				
		}
		else
		{
			block();
		}
	}
}
