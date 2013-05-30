package pl.edu.pw.elka.sag.ontology.actions;

import java.util.*;

import pl.edu.pw.elka.sag.ontology.concepts.*;

public class ChooseCarDirectionAction
{
	public Direction execute(Car car, List<Direction> directions)
	{
		Direction currentCarDirection = car.getDirection();
		
		if (currentCarDirection != null)
		{
			directions.remove(currentCarDirection.getOpposite());
		}
		
		return directions.get(new Random().nextInt(directions.size()));
	}
}
