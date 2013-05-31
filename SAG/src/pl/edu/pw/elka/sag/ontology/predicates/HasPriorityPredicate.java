package pl.edu.pw.elka.sag.ontology.predicates;

import jade.content.Predicate;
import pl.edu.pw.elka.sag.ontology.concepts.Car;

public class HasPriorityPredicate implements Predicate
{
	private static final long serialVersionUID = -5512459289233970177L;
	
	private Car higherPriorityCar;
	private Car lowerPriorityCar;
	
	public HasPriorityPredicate() { }
	
	public HasPriorityPredicate(Car higherPriorityCar, Car lowerPriorityCar)
	{
		this.higherPriorityCar = higherPriorityCar;
		this.lowerPriorityCar = lowerPriorityCar;
	}

	public Car getHigherPriorityCar()
	{
		return higherPriorityCar;
	}

	public void setHigherPriorityCar(Car higherPriorityCar)
	{
		this.higherPriorityCar = higherPriorityCar;
	}

	public Car getLowerPriorityCar()
	{
		return lowerPriorityCar;
	}

	public void setLowerPriorityCar(Car lowerPriorityCar)
	{
		this.lowerPriorityCar = lowerPriorityCar;
	}
}
