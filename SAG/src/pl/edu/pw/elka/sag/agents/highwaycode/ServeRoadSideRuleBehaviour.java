package pl.edu.pw.elka.sag.agents.highwaycode;

import jade.lang.acl.*;

import java.io.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import pl.edu.pw.elka.sag.ontology.predicates.*;

public class ServeRoadSideRuleBehaviour extends RuleBehaviourBase
{
	private static final long serialVersionUID = 2651647425904036440L;
	
	protected ServeRoadSideRuleBehaviour(HighwayCodeAgent agent)
	{
		super(agent, ConversationTypes.ROAD_SIDE_RULE_CONVERSATION_TYPE);
	}

	@Override
	protected void fillReply(ACLMessage message, ACLMessage reply) throws IOException
	{
		RoadSide roadSide = getHighwayCode().getRoadSideRule().evaluate();
		reply.setContentObject(new ShouldDriveRoadSidePredicate(roadSide));
	}
}
