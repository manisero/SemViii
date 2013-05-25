package pl.edu.pw.elka.sag.agents.city;

import pl.edu.pw.elka.sag.agents.*;

public class CityAgent extends AgentBase
{
	private static final long serialVersionUID = 4471164341534623888L;
	
	private static int CITY_WIDTH = 4;
	private static int CITY_HEIGHT = 4;

	@Override
	protected void setup()
	{
		super.setup();
		
		addBehaviour(new ServeDirectionBehaviour(CITY_WIDTH, CITY_HEIGHT));
		
		System.out.println("CityAgent " + getName()  + " is ready!");
	}
}
