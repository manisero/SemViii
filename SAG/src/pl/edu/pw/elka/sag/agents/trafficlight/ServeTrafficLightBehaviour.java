package pl.edu.pw.elka.sag.agents.trafficlight;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class ServeTrafficLightBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = 4218338469897565395L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.TRAFFIC_LIGHT_CONVERSATION_TYPE);

	private final TrafficLight trafficLight;
	
	public ServeTrafficLightBehaviour(Agent agent, TrafficLight trafficLight)
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
				reply.setContentObject(trafficLight);
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
