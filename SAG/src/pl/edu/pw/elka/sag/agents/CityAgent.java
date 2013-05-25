package pl.edu.pw.elka.sag.agents;

import jade.core.Agent;
import pl.edu.pw.elka.sag.behaviours.ServeDirectionBehaviour;

public class CityAgent extends Agent
{
	private static final long serialVersionUID = 4471164341534623888L;
	
	private static int CITY_WIDTH = 4;
	private static int CITY_HEIGHT = 4;

	@Override
	protected void setup()
	{
		addBehaviour(new ServeDirectionBehaviour(CITY_WIDTH, CITY_HEIGHT));
		
		System.out.println("CityAgent is ready!");
	}
}
