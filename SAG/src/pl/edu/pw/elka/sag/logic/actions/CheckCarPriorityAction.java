package pl.edu.pw.elka.sag.logic.actions;

import pl.edu.pw.elka.sag.ontology.concepts.*;

public class CheckCarPriorityAction
{
	public boolean execute(Car car, Car otherCar)
	{
		if (otherCar.getStatus() == CarStatus.OnCrossroads)
		{
			return false;
		}
		
		Direction carDirection = car.getDirection();
		Direction otherCarDirection = otherCar.getDirection();
		
		if (carDirection == Direction.NORTH && otherCarDirection == Direction.WEST ||
			carDirection == Direction.WEST && otherCarDirection == Direction.SOUTH ||
			carDirection == Direction.SOUTH && otherCarDirection == Direction.EAST ||
			carDirection == Direction.EAST && otherCarDirection == Direction.NORTH)
		{
			return false;
		}
		
		return true;
	}
}
