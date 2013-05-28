package pl.edu.pw.elka.sag.agents;

import pl.edu.pw.elka.sag.util.*;
import jade.core.*;

public abstract class AgentBase extends Agent
{
	private static final long serialVersionUID = -92469915112692605L;

	@Override
	protected void setup()
	{
		super.setup();
		System.out.println("Initializing " + this.getClass().getSimpleName() + " " + getLocalName());
	}
	
	protected void register()
	{
		register(null);
	}
	
	protected void register(String serviceName)
	{
		AgentRegistrar.getInstance().register(this, serviceName);
	}
	
	@Override
	protected void takeDown()
	{
		AgentRegistrar.getInstance().unregister(this);
	}
}
