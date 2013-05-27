package pl.edu.pw.elka.sag.agents.car;

import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;

import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;

public class ServeDestinationInfoBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -696095265830797259L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.DESTINATION_INFO_CONVERSATION_TYPE);
	
	public ServeDestinationInfoBehaviour(CarAgent agent)
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
				
				if (nextCrossroadsLocation == null || nextCrossroadsLocation.getX() != location.getX() || nextCrossroadsLocation.getY() != location.getY())
				{
					reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
				}
				else
				{
					reply.setPerformative(ACLMessage.INFORM);
					
					DestinationInfo destinationInfo = new DestinationInfo(getCarAgent().getDirection(), getCarAgent().getNextDirection());
					reply.setContentObject(destinationInfo);
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
