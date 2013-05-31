package pl.edu.pw.elka.sag.logic.actions;

import pl.edu.pw.elka.sag.ontology.concepts.*;

public class MoveCarAction
{
	public void execute(Car car)
	{
		Direction direction = car.getDirection();
		Location location = car.getLocation();
		
		if (direction.equals(Direction.NORTH))
		{
			location.setY(location.getY() + 1);
		}
		else if (direction.equals(Direction.SOUTH))
		{
			location.setY(location.getY() - 1);
		}
		else if (direction.equals(Direction.EAST))
		{
			location.setX(location.getX() + 1);
		}
		else if (direction.equals(Direction.WEST))
		{
			location.setX(location.getX() - 1);
		}
	}
}
