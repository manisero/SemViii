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
		
		AgentRegistrar.getInstance().register(this, getServiceName());
	}
	
	protected String getServiceName()
	{
		return null;
	}
	
	@Override
	protected void takeDown()
	{
		AgentRegistrar.getInstance().unregister(this);
	}
}
