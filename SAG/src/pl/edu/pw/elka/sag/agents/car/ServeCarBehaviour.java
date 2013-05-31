package pl.edu.pw.elka.sag.agents.car;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import pl.edu.pw.elka.sag.ontology.concepts.Location;

public class ServeCarBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -7164928311123018886L;
	private static final MessageTemplate messageTemplate = MessageTemplate.and(MessageTemplate.MatchConversationId(ConversationTypes.CAR_CONVERSATION_TYPE),
																			   MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.REQUEST),
																					   			  MessageTemplate.MatchPerformative(ACLMessage.REQUEST_WHEN)));
	
	private final Car car;
	private final CarMovementInfo movementInfo;

	public ServeCarBehaviour(Agent agent, Car car, CarMovementInfo movementInfo)
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
				ACLMessage reply = message.createReply();
				
				if (message.getPerformative() == ACLMessage.REQUEST)
				{
					handleRequest(reply);
				}
				else
				{
					handleRequestWhen(message, reply);
				}
				
				myAgent.send(reply);
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
		else
		{
			block();
		}
	}
	
	private void handleRequest(ACLMessage reply) throws IOException
	{
		reply.setPerformative(ACLMessage.INFORM);
		reply.setContentObject(car);
	}
	
	private void handleRequestWhen(ACLMessage message, ACLMessage reply) throws UnreadableException, IOException
	{
		Location location = (Location) message.getContentObject();
		Location nextCrossroadsLocation = movementInfo.getNextCrossroadsLocation();
		
		if (car.getStatus() == CarStatus.Driving || nextCrossroadsLocation == null || !location.equals(nextCrossroadsLocation))
		{
			reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
		}
		else
		{
			reply.setPerformative(ACLMessage.INFORM);
			reply.setContentObject(car);
		}
	}
}
