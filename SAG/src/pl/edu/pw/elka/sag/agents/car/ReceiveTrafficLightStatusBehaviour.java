package pl.edu.pw.elka.sag.agents.car;

import java.io.*;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import pl.edu.pw.elka.sag.ontology.predicates.*;
import pl.edu.pw.elka.sag.util.*;

public class ReceiveTrafficLightStatusBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -5012994635276751890L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.TRAFFIC_LIGHT_STATUS_CONVERSATION_TYPE);
	
	public ReceiveTrafficLightStatusBehaviour(Agent agent)
	{
		super(agent);
	}
	
	@Override
	public void action()
	{
		ACLMessage message = myAgent.receive(messageTemplate);
		
		if (message != null)
		{
			try
			{
				TrafficLightStatus status = (TrafficLightStatus) message.getContentObject();

				ACLMessage trafficRuleRequest = new ACLMessage(ACLMessage.REQUEST);
				trafficRuleRequest.addReceiver(AgentRegistry.getInstance().getHighwayCodeAgentID(myAgent));
				trafficRuleRequest.setConversationId(ConversationTypes.TRAFFIC_LIGHT_RULE_CONVERSATION_TYPE);
				trafficRuleRequest.setContentObject(new CanPassTrafficLightPredicate(status));
				
				myAgent.send(trafficRuleRequest);
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
