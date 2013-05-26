package pl.edu.pw.elka.sag.agents.trafficlight;

import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;

public class ServeTrafficLightInfoBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = 4218338469897565395L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.TRAFFIC_LIGHT_INFO_CONVERSATION_TYPE);

	public ServeTrafficLightInfoBehaviour(TrafficLightAgent agent)
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
				reply.setContentObject(new TrafficLightInfo(getTrafficLightAgent().getLocation(), getTrafficLightAgent().getAllowedDirection()));
				
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
