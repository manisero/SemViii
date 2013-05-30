package pl.edu.pw.elka.sag.ontology.predicates;

import jade.content.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class CanPassTrafficLightPredicate implements Predicate
{
	private static final long serialVersionUID = 5793262022488945714L;
	
	private TrafficLightStatus trafficLightStatus;
	
	public CanPassTrafficLightPredicate() { }
	
	public CanPassTrafficLightPredicate(TrafficLightStatus trafficLightStatus)
	{ 
		this.trafficLightStatus = trafficLightStatus;
	}

	public TrafficLightStatus getTrafficLightStatus()
	{
		return trafficLightStatus;
	}

	public void setTrafficLightStatus(TrafficLightStatus trafficLightStatus)
	{
		this.trafficLightStatus = trafficLightStatus;
	}
}
