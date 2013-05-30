package pl.edu.pw.elka.sag.ontology.predicates;

import jade.content.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class CanTurnOnCrossroads implements Predicate
{
	private static final long serialVersionUID = -3571984854080327154L;

	private Location crossroadsLocation;
	private Direction turnDirection;

	public CanTurnOnCrossroads() { }
	
	public CanTurnOnCrossroads(Location crossroadsLocation, Direction turnDirection)
	{
		this.crossroadsLocation = crossroadsLocation;
		this.turnDirection = turnDirection;
	}
	
	public Location getCrossroadsLocation()
	{
		return crossroadsLocation;
	}

	public void setCrossroadsLocation(Location crossroadsLocation)
	{
		this.crossroadsLocation = crossroadsLocation;
	}

	public Direction getTurnDirection()
	{
		return turnDirection;
	}

	public void setTurnDirection(Direction turnDirection)
	{
		this.turnDirection = turnDirection;
	}
}
