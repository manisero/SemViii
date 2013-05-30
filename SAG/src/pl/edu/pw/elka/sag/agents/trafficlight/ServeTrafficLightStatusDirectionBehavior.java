package pl.edu.pw.elka.sag.agents.trafficlight;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;
import java.util.Map;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class ServeTrafficLightStatusDirectionBehavior extends CyclicBehaviour
{
	private static final long serialVersionUID = 3479838088178976198L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.TRAFFIC_LIGHT_STATUS_CONVERSATION_TYPE);
	
	private final TrafficLight trafficLight;
	
	public ServeTrafficLightStatusDirectionBehavior(Agent agent, TrafficLight trafficLight)
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
				Direction direction = (Direction) message.getContentObject();
				Map<Direction, TrafficLightStatus> status = trafficLight.getStatus();
				
				ACLMessage reply = message.createReply();
				reply.setContentObject(status.containsKey(direction) ? status.get(direction) : null);
				
				myAgent.send(reply);
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
