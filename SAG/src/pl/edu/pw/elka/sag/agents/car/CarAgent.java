package pl.edu.pw.elka.sag.agents.car;

import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.entities.*;
import pl.edu.pw.elka.sag.exceptions.*;

public class CarAgent extends MovableTrafficAgent
{
	private static final long serialVersionUID = 258671427576035083L;
	
	@Override
	protected void setup()
	{
		super.setup();
		
		Object[] arguments = getArguments();
		
		if (arguments == null || arguments.length < 4)
		{
			throw new InvalidAgentArgumentsException();
		}
		
		int carSpeed = Integer.parseInt(arguments[3].toString());
		Direction carDirection = Direction.valueOf(arguments[2].toString());
		Car car = new Car(getLocation(), carSpeed, carDirection);
		
		addBehaviour(new MovementBehaviour(this, car, getCityAgentID()));
		addBehaviour(new ServeCarStatusInfoBehaviour(this, car));
		addBehaviour(new ReceivePossibleDirectionsBehaviour(this, car));
		addBehaviour(new ReceiveAllowedDirectionBehaviour(this, car));
		addBehaviour(new ReceiveCarStatusInfoBehaviour(this, car));
	}
}
