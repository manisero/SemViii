package pl.edu.pw.elka.sag.agents.trafficlight;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;

public class ServeAllowedDirectionBehavior extends CyclicBehaviour
{
	private static final long serialVersionUID = 3479838088178976198L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.TRAFFIC_LIGHTS_CONVERSATION_TYPE);
	
	private final TrafficLight trafficLight;
	
	public ServeAllowedDirectionBehavior(Agent agent, TrafficLight trafficLight)
	{
		super(agent);
		this.trafficLight = trafficLight;
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
				reply.setContentObject(trafficLight.getAllowedDirection());
				
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
