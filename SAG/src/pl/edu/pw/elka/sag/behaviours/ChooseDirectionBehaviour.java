package pl.edu.pw.elka.sag.behaviours;

import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;
import java.util.Random;

import pl.edu.pw.elka.sag.Direction;
import pl.edu.pw.elka.sag.DirectionsCollection;
import pl.edu.pw.elka.sag.Location;
import pl.edu.pw.elka.sag.agents.CarAgent;

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
			
			Direction direction = directions.getDirections().get(new Random().nextInt(directions.getDirections().size()));
			
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
}
