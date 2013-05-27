package pl.edu.pw.elka.sag.agents.car;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class ReceiveDestinationInfoBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -1841154680537589138L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.DESTINATION_INFO_CONVERSATION_TYPE);
	
	public ReceiveDestinationInfoBehaviour(CarAgent agent)
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
		ACLMessage message = myAgent.receive(messageTemplate);
		
		if (message != null)
		{
			try
			{
				getCarAgent().setOtherCarsChecked(getCarAgent().getOtherCarsChecked() + 1);
				
				if (message.getPerformative() != ACLMessage.INFORM)
				{
					return;
				}
				
				Direction carDirection = getCarAgent().getDirection();
				Direction otherCarDirection = ((DestinationInfo) message.getContentObject()).getFrom();
				
				if (carDirection == Direction.NORTH && otherCarDirection == Direction.WEST ||
					carDirection == Direction.WEST && otherCarDirection == Direction.SOUTH ||
					carDirection == Direction.SOUTH && otherCarDirection == Direction.EAST ||
					carDirection == Direction.EAST && otherCarDirection == Direction.NORTH)
				{
					// TODO: Stop the car
				}
				
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
}
