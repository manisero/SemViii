package pl.edu.pw.elka.sag.ontology.actions;

import pl.edu.pw.elka.sag.ontology.concepts.*;

public class GetNextCrossroadsLocationAction
{
	public Location execute(Location location, Direction direction)
	{
		if (direction == Direction.NORTH)
		{
			return new Location(location.getX(), location.getY() + 10);
		}
		else if (direction == Direction.SOUTH)
		{
			return new Location(location.getX(), location.getY() - 10);
		}
		else if (direction == Direction.EAST)
		{
			return new Location(location.getX() + 10, location.getY());
		}
		else if (direction == Direction.WEST)
		{
			return new Location(location.getX() - 10, location.getY());
		}
		else
		{
			return null;
		}
	}
}
