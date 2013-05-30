package pl.edu.pw.elka.sag.logic.highwaycode.rules.roadside;

import pl.edu.pw.elka.sag.logic.highwaycode.rules.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class RightRoadSideRule implements IRoadSideRule
{
	@Override
	public RoadSide evaluate()
	{
		return RoadSide.Right;
	}
}
