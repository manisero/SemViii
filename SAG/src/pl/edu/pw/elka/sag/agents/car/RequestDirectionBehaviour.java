package pl.edu.pw.elka.sag.agents.car;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.Location;

public class RequestDirectionBehaviour extends TickerBehaviour
{
	private static final long serialVersionUID = -111284932650857342L;
	
	private final AID cityAgentAID;
	
	public RequestDirectionBehaviour(CarAgent agent, long period, AID cityAgentAID)
	{
		super(agent, period);
		
		this.cityAgentAID = cityAgentAID;
	}

	private CarAgent getCarAgent()
	{
		return (CarAgent) myAgent;
	}
	
	@Override
	protected void onTick()
	{
		try
		{
			Location location = getCarAgent().getLocation();
			
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(cityAgentAID);
			message.setConversationId(ConversationTypes.CAR_DIRECTION_CONVERSATION_TYPE);
			message.setContentObject(location);
			
			myAgent.send(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
