package pl.edu.pw.elka.sag.agents.car;

import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;

public class ServeCarStatusInfoBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -696095265830797259L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.CAR_STATUS_INFO_CONVERSATION_TYPE);
	
	public ServeCarStatusInfoBehaviour(CarAgent agent)
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
				Location location = (Location) message.getContentObject();
				Location nextCrossroadsLocation = getCarAgent().getNextCrossroadsLocation();
				
				ACLMessage reply = message.createReply();
				
				if (getCarAgent().getStatus() == CarStatus.Driving || nextCrossroadsLocation == null || nextCrossroadsLocation.getX() != location.getX() || nextCrossroadsLocation.getY() != location.getY())
				{
					reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
				}
				else
				{
					reply.setPerformative(ACLMessage.INFORM);
					
					CarStatusInfo info = new CarStatusInfo(getCarAgent().getStatus(), getCarAgent().getDirection(), getCarAgent().getNextDirection());
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
