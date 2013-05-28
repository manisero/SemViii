package pl.edu.pw.elka.sag.agents.map;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class ReceiveTrafficLightInfoBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = 2491042257718090661L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.TRAFFIC_LIGHT_INFO_CONVERSATION_TYPE);

	private final Map map;
	
	public ReceiveTrafficLightInfoBehaviour(Agent agent, Map map)
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
				TrafficLightInfo info = (TrafficLightInfo) message.getContentObject();
				map.updateTrafficLightInfo(message.getSender(), info);			
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
