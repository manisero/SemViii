package pl.edu.pw.elka.sag.agents.trafficlight;

import jade.core.behaviours.*;

public class TrafficLightCycleBehavior extends TickerBehaviour
{
	private static final long serialVersionUID = -9007943623679750944L;

	public TrafficLightCycleBehavior(TrafficLightAgent agent, long cyclePeriod)
	{
		super(agent, cyclePeriod);
	}

	private TrafficLightAgent getTrafficLightAgent()
	{
		return (TrafficLightAgent) myAgent;
	}

	@Override
	protected void onTick()
	{
		getTrafficLightAgent().switchAllowedDirection();
	}
}
