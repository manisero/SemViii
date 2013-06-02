package pl.edu.pw.elka.sag.logic.actions;

import pl.edu.pw.elka.sag.ontology.concepts.*;

public class CheckCarPriorityAction
{
	public boolean execute(Car car, Car otherCar)
	{
		Direction carDirection = car.getDirection();
		Direction otherCarDirection;
		
		if (otherCar.getStatus() == CarStatus.NearCrossroads)
		{
			otherCarDirection = otherCar.getDirection();
		}
		else if (otherCar.getStatus() == CarStatus.OnCrossroads)
		{
			otherCarDirection = otherCar.getPreviousDirection();
		}
		else
		{
			return true;
		}
		
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
