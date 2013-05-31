package pl.edu.pw.elka.sag.logic.highwaycode.rules;

import pl.edu.pw.elka.sag.ontology.predicates.*;

public interface ITrafficLightRule
{
	public boolean evaluate(CanPassTrafficLightPredicate predicate);
}
