package pl.edu.pw.elka.sag.ontology.predicates;

import jade.content.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class CanTurnOnCrossroadsPredicate implements Predicate
{
	private static final long serialVersionUID = -3571984854080327154L;

	private Direction turnDirection;
	private Location crossroadsLocation;

	public CanTurnOnCrossroadsPredicate() { }
	
	public CanTurnOnCrossroadsPredicate(Direction turnDirection, Location crossroadsLocation)
	{
		this.turnDirection = turnDirection;
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
	
	public Location getCrossroadsLocation()
	{
		return crossroadsLocation;
	}

	public void setCrossroadsLocation(Location crossroadsLocation)
	{
		this.crossroadsLocation = crossroadsLocation;
	}
}
