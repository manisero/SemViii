package pl.edu.pw.elka.sag.agents.car;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;
import pl.edu.pw.elka.sag.ontology.concepts.Location;

public class ServeCarStatusInfoBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -696095265830797259L;
	private static final MessageTemplate messageTemplate = MessageTemplate.and(MessageTemplate.MatchConversationId(ConversationTypes.CAR_STATUS_INFO_CONVERSATION_TYPE),
																			   MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
	
	private final Car car;
	
	public ServeCarStatusInfoBehaviour(Agent agent, Car car)
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
				Location location = (Location) message.getContentObject();
				Location nextCrossroadsLocation = car.getNextCrossroadsLocation();
				
				ACLMessage reply = message.createReply();
				
				if (car.getStatus() == CarStatus.Driving || nextCrossroadsLocation == null ||
					nextCrossroadsLocation.getX() != location.getX() || nextCrossroadsLocation.getY() != location.getY())
				{
					reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
				}
				else
				{
					reply.setPerformative(ACLMessage.INFORM);
					
					CarStatusInfo info = new CarStatusInfo(car.getStatus(), car.getDirection(), car.getNextDirection());
					reply.setContentObject(info);
				}
				
				myAgent.send(reply);
			}
			catch (UnreadableException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
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
