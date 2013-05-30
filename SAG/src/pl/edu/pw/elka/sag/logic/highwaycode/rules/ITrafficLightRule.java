package pl.edu.pw.elka.sag.logic.highwaycode.rules;

import pl.edu.pw.elka.sag.ontology.concepts.*;

public interface ITrafficLightRule
{
	public boolean evaluate(Car car, TrafficLightStatus trafficLightStatus);
}
