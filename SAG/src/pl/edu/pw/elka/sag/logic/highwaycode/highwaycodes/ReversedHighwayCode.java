package pl.edu.pw.elka.sag.logic.highwaycode.highwaycodes;

import pl.edu.pw.elka.sag.logic.highwaycode.*;
import pl.edu.pw.elka.sag.logic.highwaycode.rules.*;
import pl.edu.pw.elka.sag.logic.highwaycode.rules.cartypepriority.EqualCarTypePriority;
import pl.edu.pw.elka.sag.logic.highwaycode.rules.priority.LeftHandPriorityRule;
import pl.edu.pw.elka.sag.logic.highwaycode.rules.roadside.*;
import pl.edu.pw.elka.sag.logic.highwaycode.rules.trafficlight.*;

public class ReversedHighwayCode implements IHighwayCode
{
	@Override
	public IRoadSideRule getRoadSideRule()
	{
		return new LeftRoadSideRule();
	}
	
	@Override
	public ITrafficLightRule getTrafficLightRule()
	{
		return new RedLightTrafficLightRule();
	}

	@Override
	public IPriorityRule getPriorityRule()
	{
		return new LeftHandPriorityRule();
	}

	@Override
	public ICarTypePriorityRule getCarTypePriorityRule()
	{
		return new EqualCarTypePriority();
	}	
}
