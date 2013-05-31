package pl.edu.pw.elka.sag.logic.highwaycode.rules;

import pl.edu.pw.elka.sag.ontology.predicates.HasPriorityPredicate;

public interface IPriorityRule
{
	public boolean evaluate(HasPriorityPredicate predicate);
}
