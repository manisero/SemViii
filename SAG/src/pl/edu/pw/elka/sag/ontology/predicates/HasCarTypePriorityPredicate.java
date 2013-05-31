package pl.edu.pw.elka.sag.ontology.predicates;

import jade.content.Predicate;
import pl.edu.pw.elka.sag.ontology.concepts.Car;

public class HasCarTypePriorityPredicate implements Predicate
{
	private static final long serialVersionUID = 8061245910350339156L;

	private Car higherPriorityCar;
	private Car lowerPriorityCar;
	
	public HasCarTypePriorityPredicate() { }
	
	public HasCarTypePriorityPredicate(Car higherPriorityCar, Car lowerPriorityCar)
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
