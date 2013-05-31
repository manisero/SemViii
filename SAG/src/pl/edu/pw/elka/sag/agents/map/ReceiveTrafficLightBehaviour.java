package pl.edu.pw.elka.sag.agents.map;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class ReceiveTrafficLightBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = 2491042257718090661L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.TRAFFIC_LIGHT_CONVERSATION_TYPE);

	private final Map map;
	
	public ReceiveTrafficLightBehaviour(Agent agent, Map map)
	{
		super(agent);
		this.map = map;
	}
	
	@Override
	public void action()
	{
		ACLMessage message = myAgent.receive(messageTemplate);
		
		if (message != null)
		{
			try
			{
				TrafficLight info = (TrafficLight) message.getContentObject();
				map.updateTrafficLight(message.getSender(), info);			
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
