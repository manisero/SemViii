package pl.edu.pw.elka.sag.agents;

import pl.edu.pw.elka.sag.*;
import pl.edu.pw.elka.sag.entities.*;

public class MovableTrafficAgent extends LocatableTrafficAgent implements IMovable
{
	private static final long serialVersionUID = -6842011892069749555L;

	private Direction direction = Direction.UNKNOWN;
	
	@Override
	protected void setup()
	{
		super.setup();
		addBehaviour(new ServeMovementInfoBehaviour(this));
	}
	
	@Override
	public Direction getDirection()
	{
		return direction;
	}
	
	protected void setDirection(Direction direction)
	{
		this.direction = direction;
	}
}
