package pl.edu.pw.elka.sag.agents.city;

import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.entities.*;

public class CityAgent extends AgentBase
{
	private static final long serialVersionUID = 4471164341534623888L;
	
	private static int BLOCKS_COUNT = 3;

	@Override
	protected void setup()
	{
		super.setup();
		register();
		
		City city = new City(BLOCKS_COUNT);
		addBehaviour(new ServePossibleDirectionsBehaviour(this, city));
	}
}
