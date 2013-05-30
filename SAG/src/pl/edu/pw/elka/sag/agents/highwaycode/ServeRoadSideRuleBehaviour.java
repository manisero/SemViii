package pl.edu.pw.elka.sag.agents.highwaycode;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;

import java.io.IOException;

import pl.edu.pw.elka.sag.constants.ConversationTypes;
import pl.edu.pw.elka.sag.ontology.concepts.RoadSide;
import pl.edu.pw.elka.sag.ontology.predicates.ShouldDriveRoadSidePredicate;

public class ServeRoadSideRuleBehaviour extends RuleBehaviourBase
{
	private static final long serialVersionUID = 2651647425904036440L;
	
	protected ServeRoadSideRuleBehaviour(Agent agent)
	{
		super(agent, ConversationTypes.ROAD_SIDE_RULE_CONVERSATION_TYPE);
	}

	@Override
	protected void fillReply(ACLMessage message, ACLMessage reply) throws IOException
	{
		RoadSide roadSide = getHighwayCodeAgent().getHighwayCode().getRoadSideRule().evaluate();
		reply.setContentObject(new ShouldDriveRoadSidePredicate(roadSide));
	}
}
