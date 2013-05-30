package pl.edu.pw.elka.sag.agents.map;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.util.*;

import pl.edu.pw.elka.sag.agents.car.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.util.*;

public class RequestCarMovementInfoBehaviour extends TickerBehaviour
{
	private static final long serialVersionUID = 8617902260502949902L;

	public RequestCarMovementInfoBehaviour(Agent agent, long period)
	{
		super(agent, period);
	}
	
	@Override
	protected void onTick()
	{
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		List<AID> carIds = AgentRegistry.getInstance().getAgents(myAgent, CarAgent.class);
		
		if (carIds.size() == 0)
		{
			return;
		}
		
		for (AID carId : carIds)
		{
			message.addReceiver(carId);
		}
		
		message.setConversationId(ConversationTypes.MOVEMENT_INFO_CONVERSATION_TYPE);
		
		myAgent.send(message);
	}
}
