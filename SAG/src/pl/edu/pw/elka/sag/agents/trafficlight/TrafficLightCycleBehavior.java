package pl.edu.pw.elka.sag.agents.trafficlight;

import pl.edu.pw.elka.sag.logic.actions.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import jade.core.*;
import jade.core.behaviours.*;

public class TrafficLightCycleBehavior extends TickerBehaviour
{
	private static final long serialVersionUID = -9007943623679750944L;

	private final TrafficLight trafficLight;
	
	public TrafficLightCycleBehavior(Agent agent, TrafficLight trafficLight)
	{
		super(agent, trafficLight.getCyclePeriod());
		this.trafficLight = trafficLight; 
	}

	@Override
	protected void onTick()
	{
		new SwitchTrafficLightStatusAction().execute(trafficLight);
	}
}
