package pl.edu.pw.elka.sag.agents.map;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.ontology.concepts.*;

public class ReceiveCarMovementInfoBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = 6287244284419238340L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.CAR_MOVEMENT_INFO_CONVERSATION_TYPE);

	private final Map map;
	
	public ReceiveCarMovementInfoBehaviour(Agent agent, Map map)
	{
		super(agent);
		this.map = map;
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
				map.updateCarInfo(message.getSender(), info);
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
