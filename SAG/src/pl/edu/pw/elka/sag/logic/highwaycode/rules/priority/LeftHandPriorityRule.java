package pl.edu.pw.elka.sag.logic.highwaycode.rules.priority;

import pl.edu.pw.elka.sag.ontology.concepts.Car;
import pl.edu.pw.elka.sag.ontology.predicates.HasPriorityPredicate;

public class LeftHandPriorityRule extends PriorityRuleBase
{
	@Override
	public boolean evaluate(HasPriorityPredicate predicate)
	{
		Car higherPriorityCar = predicate.getHigherPriorityCar();
		Car lowerPriorityCar = predicate.getLowerPriorityCar();
		
		if (isGoingStraight(higherPriorityCar))
		{
			return isOnTheLeft(higherPriorityCar, lowerPriorityCar) ? false : true;
		}
		else if (isTurningLeft(higherPriorityCar))
		{
			return true;
		}
		else
		{
			if (isOnTheLeft(higherPriorityCar, lowerPriorityCar))
			{
				return false;
			}
			
			if (isAcross(higherPriorityCar, lowerPriorityCar) && (isGoingStraight(lowerPriorityCar) || isTurningLeft(lowerPriorityCar)))
			{
				return false;
			}
			
			return true;
		}
	}
}
