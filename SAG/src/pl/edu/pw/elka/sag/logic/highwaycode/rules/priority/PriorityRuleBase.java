package pl.edu.pw.elka.sag.logic.highwaycode.rules.priority;

import pl.edu.pw.elka.sag.logic.highwaycode.rules.IPriorityRule;
import pl.edu.pw.elka.sag.ontology.concepts.Car;
import pl.edu.pw.elka.sag.ontology.concepts.Direction;

public abstract class PriorityRuleBase implements IPriorityRule
{
	protected boolean isGoingStraight(Car car)
	{
		return car.getNextDirection().equals(car.getDirection());
	}
	
	protected boolean isTurningRight(Car car)
	{
		return 	car.getDirection().equals(Direction.NORTH) && car.getNextDirection().equals(Direction.EAST) ||
				car.getDirection().equals(Direction.EAST) && car.getNextDirection().equals(Direction.SOUTH) ||
				car.getDirection().equals(Direction.SOUTH) && car.getNextDirection().equals(Direction.WEST) ||
				car.getDirection().equals(Direction.WEST) && car.getNextDirection().equals(Direction.NORTH);
	}
	
	protected boolean isTurningLeft(Car car)
	{
		return  car.getDirection().equals(Direction.NORTH) && car.getNextDirection().equals(Direction.WEST) ||
				car.getDirection().equals(Direction.WEST) && car.getNextDirection().equals(Direction.SOUTH) ||
				car.getDirection().equals(Direction.SOUTH) && car.getNextDirection().equals(Direction.EAST) ||
				car.getDirection().equals(Direction.EAST) && car.getNextDirection().equals(Direction.NORTH);
	}
	
	protected boolean isAcross(Car car, Car otherCar)
	{
		return car.getDirection().getOpposite().equals(otherCar.getDirection());
	}
	
	protected boolean isOnTheRight(Car car, Car otherCar)
	{
		return	car.getDirection().equals(Direction.NORTH) && otherCar.getDirection().equals(Direction.WEST) ||
				car.getDirection().equals(Direction.WEST) && otherCar.getDirection().equals(Direction.SOUTH) ||
				car.getDirection().equals(Direction.SOUTH) && otherCar.getDirection().equals(Direction.EAST) ||
				car.getDirection().equals(Direction.EAST) && otherCar.getDirection().equals(Direction.NORTH);
	}
	
	protected boolean isOnTheLeft(Car car, Car otherCar)
	{
		return	car.getDirection().equals(Direction.NORTH) && otherCar.getDirection().equals(Direction.EAST) ||
				car.getDirection().equals(Direction.EAST) && otherCar.getDirection().equals(Direction.SOUTH) ||
				car.getDirection().equals(Direction.SOUTH) && otherCar.getDirection().equals(Direction.WEST) ||
				car.getDirection().equals(Direction.WEST) && otherCar.getDirection().equals(Direction.NORTH);
	}
}
