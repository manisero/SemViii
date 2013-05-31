package pl.edu.pw.elka.sag.logic.highwaycode.rules.cartypepriority;

import pl.edu.pw.elka.sag.logic.highwaycode.rules.ICarTypePriorityRule;
import pl.edu.pw.elka.sag.ontology.predicates.HasCarTypePriorityPredicate;

public class StandardCarTypePriority implements ICarTypePriorityRule
{
	@Override
	public boolean evaluate(HasCarTypePriorityPredicate predicate)
	{
		return predicate.getHigherPriorityCar().getType().getValue() > predicate.getLowerPriorityCar().getType().getValue();
	}
}
