package pl.edu.pw.elka.sag.logic.highwaycode.highwaycodes;

import pl.edu.pw.elka.sag.logic.highwaycode.*;
import pl.edu.pw.elka.sag.logic.highwaycode.rules.*;
import pl.edu.pw.elka.sag.logic.highwaycode.rules.cartypepriority.StandardCarTypePriority;
import pl.edu.pw.elka.sag.logic.highwaycode.rules.priority.RightHandPriorityRule;
import pl.edu.pw.elka.sag.logic.highwaycode.rules.roadside.*;
import pl.edu.pw.elka.sag.logic.highwaycode.rules.trafficlight.*;

public class PolishHighwayCode implements IHighwayCode
{
	@Override
	public IRoadSideRule getRoadSideRule()
	{
		return new RightRoadSideRule();
	}
	
	@Override
	public ITrafficLightRule getTrafficLightRule()
	{
		return new GreenLightTrafficLightRule();
	}

	@Override
	public IPriorityRule getPriorityRule()
	{
		return new RightHandPriorityRule();
	}

	@Override
	public ICarTypePriorityRule getCarTypePriorityRule()
	{
		return new StandardCarTypePriority();
	}
}
