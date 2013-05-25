package pl.edu.pw.elka.sag.agents.trafficlight;

import pl.edu.pw.elka.sag.agents.*;

public class TrafficLightAgent extends LocatableAgent
{
	private static final long serialVersionUID = 8004867227278039286L;
	
	@Override
	protected void setup()
	{
		super.setup();
		System.out.println("TrafficLightAgent " + getName()  + " is ready!");
	}
}
