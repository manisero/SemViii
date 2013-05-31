package pl.edu.pw.elka.sag.agents.car;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

import pl.edu.pw.elka.sag.constants.ConversationTypes;
import pl.edu.pw.elka.sag.ontology.concepts.Car;
import pl.edu.pw.elka.sag.ontology.concepts.CarMovementInfo;
import pl.edu.pw.elka.sag.ontology.predicates.HasPriorityPredicate;
import pl.edu.pw.elka.sag.util.AgentSearchUtilities;

public class ReceiveCarBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -1841154680537589138L;
	private static final MessageTemplate messageTemplate = MessageTemplate.and(MessageTemplate.MatchConversationId(ConversationTypes.CAR_CONVERSATION_TYPE),
																			   MessageTemplate.and(MessageTemplate.not(MessageTemplate.MatchPerformative(ACLMessage.REQUEST)),
																					   			   MessageTemplate.not(MessageTemplate.MatchPerformative(ACLMessage.REQUEST_WHEN))));
	
	private final Car car;
	private final CarMovementInfo movementInfo;
	
	public ReceiveCarBehaviour(Agent agent, Car car, CarMovementInfo movementInfo)
	{
		super(agent);
		this.car = car;
		this.movementInfo = movementInfo;
	}
	
	@Override
	public void action()
	{
		ACLMessage message = myAgent.receive(messageTemplate);
		
		if (message != null)
		{
			try
			{
				movementInfo.setOtherCarsChecked(movementInfo.getOtherCarsChecked() + 1);
				
				if (message.getPerformative() == ACLMessage.NOT_UNDERSTOOD)
				{
					return;
				}
				
				checkPriority((Car) message.getContentObject());
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
	
	private void checkPriority(Car otherCar)
	{
		try
		{
			ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
			message.addReceiver(AgentSearchUtilities.findHighwayCodeAgent(myAgent));
			message.setConversationId(ConversationTypes.PRIORITY_RULE_CONVERSATION_TYPE);
			message.setContentObject(new HasPriorityPredicate(car, otherCar));
			
			myAgent.send(message);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
