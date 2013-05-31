package pl.edu.pw.elka.sag.logic.highwaycode.rules.priority;

import pl.edu.pw.elka.sag.logic.highwaycode.rules.IPriorityRule;
import pl.edu.pw.elka.sag.ontology.concepts.Direction;
import pl.edu.pw.elka.sag.ontology.predicates.HasPriorityPredicate;

public class LeftHandPriorityRule implements IPriorityRule
{
	@Override
	public boolean evaluate(HasPriorityPredicate predicate)
	{
		if (predicate.getHigherPriorityDirection() == Direction.NORTH && predicate.getLowerPriorityDirection() == Direction.EAST ||
			predicate.getHigherPriorityDirection() == Direction.WEST && predicate.getLowerPriorityDirection() == Direction.NORTH ||
			predicate.getHigherPriorityDirection() == Direction.SOUTH && predicate.getLowerPriorityDirection() == Direction.WEST ||
			predicate.getHigherPriorityDirection() == Direction.EAST && predicate.getLowerPriorityDirection() == Direction.SOUTH)
		{
			return false;
		}
				
		return true;
	}
}
