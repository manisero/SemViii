package pl.edu.pw.elka.sag.agents.trafficlight;

import jade.core.behaviours.*;

public class TrafficLightCycleBehavior extends CyclicBehaviour
{
	private static final long serialVersionUID = -9007943623679750944L;

	public TrafficLightCycleBehavior(TrafficLightAgent agent)
	{
		super(agent);
	}

	private TrafficLightAgent getTrafficLightAgent()
	{
		return (TrafficLightAgent) myAgent;
	}
	
	@Override
	public void action()
	{
		getTrafficLightAgent().switchAllowedDirection();
	}
}
