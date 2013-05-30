package pl.edu.pw.elka.sag.ontology.actions;

import pl.edu.pw.elka.sag.ontology.concepts.*;

public class CheckCarPriorityAction
{
	public boolean execute(Car car, CarStatusInfo otherCarStatus)
	{
		if (otherCarStatus.getStatus() == CarStatus.OnCrossroads)
		{
			return false;
		}
		
		Direction carDirection = car.getDirection();
		Direction otherCarDirection = otherCarStatus.getFrom();
		
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
