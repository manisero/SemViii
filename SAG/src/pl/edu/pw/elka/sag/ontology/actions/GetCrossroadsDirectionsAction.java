package pl.edu.pw.elka.sag.ontology.actions;

import java.util.*;

import pl.edu.pw.elka.sag.ontology.concepts.*;

public class GetCrossroadsDirectionsAction
{
	public Collection<Direction> execute(City city, Location location)
	{
		List<Direction> directions = new LinkedList<Direction>();
		
		if (location.getX() > 0)
		{
			directions.add(Direction.WEST);
		}
		
		if (location.getX() < city.getSize() - 1)
		{
			directions.add(Direction.EAST);
		}
		
		if (location.getY() > 0)
		{
			directions.add(Direction.SOUTH);
		}
		
		if (location.getY() < city.getSize() - 1)
		{
			directions.add(Direction.NORTH);
		}
		
		return directions;
	}
}
