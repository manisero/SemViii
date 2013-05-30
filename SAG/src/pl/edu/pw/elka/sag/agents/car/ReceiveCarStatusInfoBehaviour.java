package pl.edu.pw.elka.sag.agents.car;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.logic.actions.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

public class ReceiveCarStatusInfoBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -1841154680537589138L;
	private static final MessageTemplate messageTemplate = MessageTemplate.and(MessageTemplate.MatchConversationId(ConversationTypes.CAR_STATUS_INFO_CONVERSATION_TYPE),
			   																   MessageTemplate.not(MessageTemplate.MatchPerformative(ACLMessage.REQUEST)));
	
	private final Car car;
	
	public ReceiveCarStatusInfoBehaviour(Agent agent, Car car)
	{
		super(agent);
		this.car = car;
	}
	
	@Override
	public void action()
	{
		ACLMessage message = myAgent.receive(messageTemplate);
		
		if (message != null)
		{
			try
			{
				car.setOtherCarsChecked(car.getOtherCarsChecked() + 1);
				
				if (message.getPerformative() != ACLMessage.INFORM)
				{
					return;
				}
				
				CarStatusInfo info = (CarStatusInfo) message.getContentObject();
				
				if (!new CheckCarPriorityAction().execute(car, info))
				{
					car.setHasPriority(false);
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
