package pl.edu.pw.elka.sag.ontology.predicates;

import pl.edu.pw.elka.sag.ontology.concepts.*;
import jade.content.*;

public class ShouldDriveRoadSidePredicate implements Predicate
{
	private static final long serialVersionUID = 8223499419801839767L;

	private RoadSide roadSide;

	public ShouldDriveRoadSidePredicate() { }
	
	public ShouldDriveRoadSidePredicate(RoadSide roadSide)
	{
		this.roadSide = roadSide;
	}
	
	public RoadSide getRoadSide()
	{
		return roadSide;
	}

	public void setRoadSide(RoadSide roadSide)
	{
		this.roadSide = roadSide;
	}
}
