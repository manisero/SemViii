package pl.edu.pw.elka.sag.ontology.predicates;

import jade.content.Predicate;
import pl.edu.pw.elka.sag.ontology.concepts.Direction;

public class HasPriorityPredicate implements Predicate
{
	private static final long serialVersionUID = -5512459289233970177L;
	
	private Direction higherPriorityDirection;
	private Direction lowerPriorityDirection;
	
	public HasPriorityPredicate() { }
	
	public HasPriorityPredicate(Direction higherPriorityDirection, Direction lowerPriorityDirection)
	{
		this.higherPriorityDirection = higherPriorityDirection;
		this.lowerPriorityDirection = lowerPriorityDirection;
	}

	public Direction getHigherPriorityDirection()
	{
		return higherPriorityDirection;
	}

	public void setHigherPriorityDirection(Direction higherPriorityDirection)
	{
		this.higherPriorityDirection = higherPriorityDirection;
	}

	public Direction getLowerPriorityDirection()
	{
		return lowerPriorityDirection;
	}

	public void setLowerPriorityDirection(Direction lowerPriorityDirection)
	{
		this.lowerPriorityDirection = lowerPriorityDirection;
	}
}
