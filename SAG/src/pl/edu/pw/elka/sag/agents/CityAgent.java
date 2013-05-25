package pl.edu.pw.elka.sag.agents;

import jade.core.Agent;

public class CityAgent extends Agent
{
	private static final long serialVersionUID = 4471164341534623888L;

	@Override
	protected void setup()
	{
		System.out.println("Hello world, I am: " + getAID().getName());
	}
}
