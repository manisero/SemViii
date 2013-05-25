package pl.edu.pw.elka.sag.agents;

import pl.edu.pw.elka.sag.util.*;
import jade.core.*;

public class AgentBase extends Agent
{
	private static final long serialVersionUID = -92469915112692605L;

	@Override
	protected void setup()
	{
		AgentRegistrar.getInstance().register(this);
	}
	
	@Override
	protected void takeDown()
	{
		AgentRegistrar.getInstance().unregister(this);
	}
}
