package pl.edu.pw.elka.sag.agents.trafficlight;

import pl.edu.pw.elka.sag.agents.*;
import pl.edu.pw.elka.sag.entities.*;

public class TrafficLightAgent extends LocatableTrafficAgent
{
	private static final long serialVersionUID = 8004867227278039286L;
	
	private Direction allowedDirection = Direction.NORTH_SOUTH;
	
	@Override
	protected void setup()
	{
		super.setup();
		addBehaviour(new TrafficLightCycleBehavior(this, 2000));
		addBehaviour(new ServeAllowedDirectionBehavior(this));
		addBehaviour(new ServeTrafficLightInfoBehaviour(this));
	}
	
	public Direction getAllowedDirection()
	{
		return allowedDirection;
	}
	
	public void switchAllowedDirection()
	{
		allowedDirection = allowedDirection.getOpposite();
	}
}
