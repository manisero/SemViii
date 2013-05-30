package pl.edu.pw.elka.sag.logic.highwaycode;

import pl.edu.pw.elka.sag.logic.highwaycode.rules.trafficlight.*;

public interface IHighwayCode
{
	public ITrafficLightRule getTrafficLightRule();
}
