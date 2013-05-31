package pl.edu.pw.elka.sag.logic.highwaycode.rules.priority;

import pl.edu.pw.elka.sag.ontology.concepts.Car;
import pl.edu.pw.elka.sag.ontology.predicates.HasPriorityPredicate;

public class RightHandPriorityRule extends PriorityRuleBase
{
	@Override
	public boolean evaluate(HasPriorityPredicate predicate)
	{
		Car higherPriorityCar = predicate.getHigherPriorityCar();
		Car lowerPriorityCar = predicate.getLowerPriorityCar();
		
		if (isGoingStraight(higherPriorityCar))
		{
			return isOnTheRight(higherPriorityCar, lowerPriorityCar) ? false : true;
		}
		else if (isTurningRight(higherPriorityCar))
		{
			return true;
		}
		else
		{
			if (isOnTheRight(higherPriorityCar, lowerPriorityCar))
			{
				return false;
			}
			
			if (isAcross(higherPriorityCar, lowerPriorityCar) && (isGoingStraight(lowerPriorityCar) || isTurningRight(lowerPriorityCar)))
			{
				return false;
			}
			
			return true;
		}
	}
}
