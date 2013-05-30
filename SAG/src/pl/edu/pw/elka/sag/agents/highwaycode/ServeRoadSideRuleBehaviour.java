package pl.edu.pw.elka.sag.agents.highwaycode;

import java.io.*;

import jade.core.*;
import jade.lang.acl.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.logic.highwaycode.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import pl.edu.pw.elka.sag.ontology.predicates.*;

public class ServeRoadSideRuleBehaviour extends RuleBehaviourBase
{
	private static final long serialVersionUID = 2651647425904036440L;
	
	protected ServeRoadSideRuleBehaviour(Agent agent, IHighwayCode highwayCode)
	{
		super(agent, highwayCode, ConversationTypes.ROAD_SIDE_RULE_CONVERSATION_TYPE);
	}

	@Override
	protected void fillReply(ACLMessage message, ACLMessage reply) throws IOException
	{
		RoadSide roadSide = highwayCode.getRoadSideRule().evaluate();
		reply.setContentObject(new ShouldDriveRoadSidePredicate(roadSide));
	}
}
