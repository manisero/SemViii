package pl.edu.pw.elka.sag.agents.car;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;
import java.util.*;

import pl.edu.pw.elka.sag.entities.*;
import pl.edu.pw.elka.sag.entities.Location;

public class ChooseDirectionBehaviour extends TickerBehaviour
{
	private static final long serialVersionUID = -111284932650857342L;
	
	private AID cityAgentAID;
	
	public ChooseDirectionBehaviour(CarAgent agent, long period, AID cityAgentAID)
	{
		super(agent, period);
		
		this.cityAgentAID = cityAgentAID;
	}

	@Override
	protected void onTick()
	{
		try
		{
			Location location = getCarAgent().getLocation();
			
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);

			message.addReceiver(cityAgentAID);
			message.setContentObject(location);
			
			myAgent.send(message);
			
			ACLMessage reply = myAgent.blockingReceive();
			
			DirectionsCollection directions = (DirectionsCollection) reply.getContentObject();
			
			Direction direction = chooseDirection(directions);
			
			getCarAgent().move(direction);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (UnreadableException e)
		{
			e.printStackTrace();
		}
	}
	
	private CarAgent getCarAgent()
	{
		return (CarAgent) myAgent;
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
