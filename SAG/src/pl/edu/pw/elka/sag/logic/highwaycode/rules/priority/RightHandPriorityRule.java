package pl.edu.pw.elka.sag.logic.highwaycode.rules.priority;

import pl.edu.pw.elka.sag.logic.highwaycode.rules.IPriorityRule;
import pl.edu.pw.elka.sag.ontology.concepts.Direction;
import pl.edu.pw.elka.sag.ontology.predicates.HasPriorityPredicate;

public class RightHandPriorityRule implements IPriorityRule
{
	@Override
	public boolean evaluate(HasPriorityPredicate predicate)
	{
		if (predicate.getHigherPriorityDirection() == Direction.NORTH && predicate.getLowerPriorityDirection() == Direction.WEST ||
			predicate.getHigherPriorityDirection() == Direction.WEST && predicate.getLowerPriorityDirection() == Direction.SOUTH ||
			predicate.getHigherPriorityDirection() == Direction.SOUTH && predicate.getLowerPriorityDirection() == Direction.EAST ||
			predicate.getHigherPriorityDirection() == Direction.EAST && predicate.getLowerPriorityDirection() == Direction.NORTH)
		{
			return false;
		}
			
		return true;
	}
}
