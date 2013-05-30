package pl.edu.pw.elka.sag.agents.highwaycode;

import pl.edu.pw.elka.sag.agents.*;

public class HighwayCodeAgent extends AgentBase
{
	private static final long serialVersionUID = 1567080874884137549L;

	@Override
	protected void setup()
	{
		super.setup();
		register();
		
		addBehaviour(new ServeTrafficLightRuleBehaviour(this));
	}
}
