package pl.edu.pw.elka.sag.agents.map;

import java.io.*;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.predicates.*;
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
		try
		{
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(AgentSearchUtilities.findHighwayCodeAgent(myAgent));
			message.setConversationId(ConversationTypes.ROAD_SIDE_RULE_CONVERSATION_TYPE);
			message.setContentObject(new ShouldDriveRoadSidePredicate());
			
			myAgent.send(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
