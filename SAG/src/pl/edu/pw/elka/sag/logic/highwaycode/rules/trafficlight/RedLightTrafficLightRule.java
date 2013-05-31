package pl.edu.pw.elka.sag.logic.highwaycode.rules.trafficlight;

import pl.edu.pw.elka.sag.logic.highwaycode.rules.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import pl.edu.pw.elka.sag.ontology.predicates.*;

public class RedLightTrafficLightRule implements ITrafficLightRule
{
	@Override
	public boolean evaluate(CanPassTrafficLightPredicate predicate)
	{
		return predicate.getTrafficLightStatus() == TrafficLightStatus.RED;
	}
}
