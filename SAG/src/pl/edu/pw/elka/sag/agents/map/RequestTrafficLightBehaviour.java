package pl.edu.pw.elka.sag.agents.map;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.util.*;

import pl.edu.pw.elka.sag.agents.trafficlight.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.util.*;

public class RequestTrafficLightBehaviour extends TickerBehaviour
{
	private static final long serialVersionUID = -1109817226231701031L;

	public RequestTrafficLightBehaviour(Agent agent, long period)
	{
		super(agent, period);
	}
	
	@Override
	protected void onTick()
	{
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		List<AID> trafficLightIds = AgentRegistry.getInstance().getAgents(myAgent, TrafficLightAgent.class);
		
		if (trafficLightIds.size() == 0)
		{
			return;
		}
		
		for (AID trafficLightId : trafficLightIds)
		{
			message.addReceiver(trafficLightId);
		}
		
		message.setConversationId(ConversationTypes.TRAFFIC_LIGHT_CONVERSATION_TYPE);
		
		myAgent.send(message);
	}
}
