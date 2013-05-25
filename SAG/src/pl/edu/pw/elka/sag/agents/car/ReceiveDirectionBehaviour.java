package pl.edu.pw.elka.sag.agents.car;

import java.util.*;

import pl.edu.pw.elka.sag.entities.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class ReceiveDirectionBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -6171122937430472879L;

	public ReceiveDirectionBehaviour(CarAgent agent)
	{
		super(agent);
	}
	
	private CarAgent getCarAgent()
	{
		return (CarAgent) myAgent;
	}
	
	@Override
	public void action()
	{
		ACLMessage message = myAgent.receive();
		
		if (message != null)
		{
			try
			{
				DirectionsCollection directions = (DirectionsCollection) message.getContentObject();
				
				Direction direction = chooseDirection(directions);
				getCarAgent().move(direction);				
			}
			catch (UnreadableException e)
			{
				e.printStackTrace();
			}				
		}
		else
		{
			block();
		}
	}
	
	private Direction chooseDirection(DirectionsCollection directions)
	{
		List<Direction> directionsList = directions.getDirections();
		
		Direction currentCarDirection = getCarAgent().getDirection();
		
		if (currentCarDirection != null)
		{
			directionsList.remove(currentCarDirection.getOppositeDirection());
		}
		
		return directionsList.get(new Random().nextInt(directionsList.size()));
	}
}
