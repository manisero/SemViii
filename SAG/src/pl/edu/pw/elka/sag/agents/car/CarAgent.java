package pl.edu.pw.elka.sag.agents.car;

import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.exceptions.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import pl.edu.pw.elka.sag.util.*;

public class CarAgent extends AgentBase
{
	private static final long serialVersionUID = 258671427576035083L;
	
	@Override
	protected void setup()
	{
		super.setup();
		register();
		
		Object[] arguments = getArguments();
		
		if (arguments == null || arguments.length < 4)
		{
			throw new InvalidAgentArgumentsException();
		}
		
		Location carLocation = ArgumentsUtilities.getLocation(arguments, 0, 1);
		int carSpeed = ArgumentsUtilities.getInt(arguments, 2);
		Direction carDirection = ArgumentsUtilities.getDirection(arguments, 3);
		Car car = new Car(carLocation, carSpeed, carDirection);
		
		addBehaviour(new MovementBehaviour(this, car));
		addBehaviour(new ServeMovementInfoBehaviour(this, car));
		addBehaviour(new ServeCarStatusInfoBehaviour(this, car));
		addBehaviour(new ReceivePossibleDirectionsBehaviour(this, car));
		addBehaviour(new ReceiveTrafficLightStatusBehaviour(this));
		addBehaviour(new ReceiveTrafficLightRuleBehaviour(this, car));
		addBehaviour(new ReceiveCarStatusInfoBehaviour(this, car));
	}
}
