package pl.edu.pw.elka.sag.logic.highwaycode.rules.cartypepriority;

import pl.edu.pw.elka.sag.logic.highwaycode.rules.ICarTypePriorityRule;
import pl.edu.pw.elka.sag.ontology.predicates.HasCarTypePriorityPredicate;

public class EqualCarTypePriority implements ICarTypePriorityRule
{
	@Override
	public boolean evaluate(HasCarTypePriorityPredicate predicate)
	{
		return false;
	}
}
