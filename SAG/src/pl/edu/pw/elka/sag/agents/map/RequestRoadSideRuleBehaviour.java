package pl.edu.pw.elka.sag.agents.map;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.util.*;

public class RequestRoadSideRuleBehaviour extends TickerBehaviour
{
	private static final long serialVersionUID = -5529502916392665754L;

	public RequestRoadSideRuleBehaviour(Agent agent, long period)
	{
		super(agent, period);
	}
	
	@Override
	protected void onTick()
	{
		ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
		message.addReceiver(AgentRegistry.getInstance().getHighwayCodeAgentID(myAgent));
		message.setConversationId(ConversationTypes.ROAD_SIDE_RULE_CONVERSATION_TYPE);
		
		myAgent.send(message);
	}
}
