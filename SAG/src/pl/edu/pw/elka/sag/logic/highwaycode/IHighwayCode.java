package pl.edu.pw.elka.sag.logic.highwaycode;

import pl.edu.pw.elka.sag.logic.highwaycode.rules.*;

public interface IHighwayCode
{
	public IRoadSideRule getRoadSideRule();
	
	public ITrafficLightRule getTrafficLightRule();
	
	public IPriorityRule getPriorityRule();
	
	public ICarTypePriorityRule getCarTypePriorityRule();
}
