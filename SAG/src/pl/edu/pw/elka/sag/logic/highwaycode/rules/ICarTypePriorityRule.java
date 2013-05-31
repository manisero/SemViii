package pl.edu.pw.elka.sag.logic.highwaycode.rules;

import pl.edu.pw.elka.sag.ontology.predicates.HasCarTypePriorityPredicate;

public interface ICarTypePriorityRule
{
	public boolean evaluate(HasCarTypePriorityPredicate predicate);
}
