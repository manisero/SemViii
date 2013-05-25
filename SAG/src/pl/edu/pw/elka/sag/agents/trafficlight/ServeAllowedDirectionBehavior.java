package pl.edu.pw.elka.sag.agents.trafficlight;

import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;

import pl.edu.pw.elka.sag.constants.*;

public class ServeAllowedDirectionBehavior extends CyclicBehaviour
{
	private static final long serialVersionUID = 3479838088178976198L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.TRAFFIC_LIGHTS_CONVERSATION_TYPE);
	
	public ServeAllowedDirectionBehavior(TrafficLightAgent agent)
	{
		super(agent);
	}
	
	private TrafficLightAgent getTrafficLightAgent()
	{
		return (TrafficLightAgent) myAgent;
	}
	
	@Override
	public void action()
	{
		ACLMessage message = myAgent.receive(messageTemplate);
		
		if (message != null)
		{
			try
			{
				ACLMessage reply = message.createReply();
				reply.setContentObject(getTrafficLightAgent().getAllowedDirection());
				
				myAgent.send(reply);
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
