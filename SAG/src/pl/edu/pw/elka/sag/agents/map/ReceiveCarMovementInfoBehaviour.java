package pl.edu.pw.elka.sag.agents.map;

import jade.core.behaviours.*;
import jade.lang.acl.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;

public class ReceiveCarMovementInfoBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = 6287244284419238340L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.MOVEMENT_INFO_CONVERSATION_TYPE);

	public ReceiveCarMovementInfoBehaviour(MapAgent agent)
	{
		super(agent);
	}
	
	private MapAgent getMapAgent()
	{
		return (MapAgent) myAgent;
	}
	
	@Override
	public void action()
	{
		ACLMessage message = myAgent.receive(messageTemplate);
		
		if (message != null)
		{
			try
			{
				MovementInfo info = (MovementInfo) message.getContentObject();
				getMapAgent().updateCarInfo(message.getSender(), info);
								
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
