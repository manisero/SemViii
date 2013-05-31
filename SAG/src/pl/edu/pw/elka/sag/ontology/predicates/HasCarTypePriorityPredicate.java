package pl.edu.pw.elka.sag.ontology.predicates;

import jade.content.Predicate;
import pl.edu.pw.elka.sag.ontology.concepts.Car;

public class HasCarTypePriorityPredicate implements Predicate
{
	private static final long serialVersionUID = 8061245910350339156L;

	private Car car;
	
	public HasCarTypePriorityPredicate() { }
	
	public HasCarTypePriorityPredicate(Car car)
	{
		this.setCar(car);
	}

	public Car getCar()
	{
		return car;
	}

	public void setCar(Car car)
	{
		this.car = car;
	}
}
