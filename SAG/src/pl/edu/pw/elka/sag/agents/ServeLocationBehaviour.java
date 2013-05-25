package pl.edu.pw.elka.sag.agents;

import pl.edu.pw.elka.sag.*;
import jade.core.behaviours.*;

public class ServeLocationBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -624286605302240972L;
	
	private ILocatable locatable;

	public ServeLocationBehaviour(ILocatable locatable)
	{
		this.locatable = locatable;
	}
	
	@Override
	public void action()
	{
		
	}
}
