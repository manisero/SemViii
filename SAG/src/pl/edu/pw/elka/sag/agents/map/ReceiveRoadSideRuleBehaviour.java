package pl.edu.pw.elka.sag.agents.map;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class ReceiveRoadSideRuleBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = 6858684117858172367L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.ROAD_SIDE_RULE_CONVERSATION_TYPE);

	private final Map map;
	
	public ReceiveRoadSideRuleBehaviour(Agent agent, Map map)
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
				RoadSide roadSide = (RoadSide) message.getContentObject();
				map.updateRoadSide(roadSide);			
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
