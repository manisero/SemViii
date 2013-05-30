package pl.edu.pw.elka.sag.agents.highwaycode;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class ServeTrafficLightRuleBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -8705231544834694720L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.TRAFFIC_LIGHT_RULE_CONVERSATION_TYPE);
	
	public ServeTrafficLightRuleBehaviour(Agent agent)
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
				TrafficLightStatus trafficLightStatus = (TrafficLightStatus) message.getContentObject();
				
				ACLMessage reply = message.createReply();
				
				if (trafficLightStatus == TrafficLightStatus.GREEN)
				{
					reply.setPerformative(ACLMessage.AGREE);
				}
				else
				{
					reply.setPerformative(ACLMessage.REFUSE);
				}
				
				myAgent.send(reply);
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
