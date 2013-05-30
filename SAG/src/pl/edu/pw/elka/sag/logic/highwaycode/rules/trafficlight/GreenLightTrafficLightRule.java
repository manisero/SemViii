package pl.edu.pw.elka.sag.logic.highwaycode.rules.trafficlight;

import pl.edu.pw.elka.sag.logic.highwaycode.rules.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class GreenLightTrafficLightRule implements ITrafficLightRule
{
	@Override
	public boolean evaluate(Car car, TrafficLightStatus trafficLightStatus)
	{
		return trafficLightStatus == TrafficLightStatus.GREEN;
	}
}
