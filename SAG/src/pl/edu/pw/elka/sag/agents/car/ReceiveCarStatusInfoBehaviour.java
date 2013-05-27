package pl.edu.pw.elka.sag.agents.car;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class ReceiveCarStatusInfoBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -1841154680537589138L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.CAR_STATUS_INFO_CONVERSATION_TYPE);
	
	public ReceiveCarStatusInfoBehaviour(CarAgent agent)
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
				
				CarStatusInfo info = (CarStatusInfo) message.getContentObject();
				
				if (info.getStatus() == CarStatus.OnCrossroads)
				{
					getCarAgent().setHasPriority(false);
					return;
				}
				
				Direction carDirection = getCarAgent().getDirection();
				Direction otherCarDirection = info.getFrom();
				
				if (carDirection == Direction.NORTH && otherCarDirection == Direction.WEST ||
					carDirection == Direction.WEST && otherCarDirection == Direction.SOUTH ||
					carDirection == Direction.SOUTH && otherCarDirection == Direction.EAST ||
					carDirection == Direction.EAST && otherCarDirection == Direction.NORTH)
				{
					getCarAgent().setHasPriority(false);
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
