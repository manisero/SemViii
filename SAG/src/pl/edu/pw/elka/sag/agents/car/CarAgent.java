package pl.edu.pw.elka.sag.agents.car;

import java.awt.Color;

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
		CarType type = arguments.length > 4 ? ArgumentsUtilities.getCarType(arguments, 4) : CarType.STANDARD;
		Color color = arguments.length > 5 ? ArgumentsUtilities.getColor(arguments, 5) : type.getDefaultTypeColor();
		Car car = new Car(carLocation, carSpeed, carDirection, type, color);
		CarMovementInfo movementInfo = new CarMovementInfo();
		
		addBehaviour(new MovementBehaviour(this, car, movementInfo));
		addBehaviour(new ServeCarBehaviour(this, car, movementInfo));
		addBehaviour(new ReceivePossibleDirectionsBehaviour(this, car));
		addBehaviour(new ReceiveTrafficLightStatusBehaviour(this));
		addBehaviour(new ReceiveTrafficLightRuleBehaviour(this, movementInfo));
		addBehaviour(new ReceiveCarBehaviour(this, car, movementInfo));
		addBehaviour(new ReceivePriorityRuleBehaviour(this, movementInfo));
		addBehaviour(new ReceiveTypePriorityRuleBehaviour(this, movementInfo));
	}
}
