package pl.edu.pw.elka.sag.agents;

import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;

import pl.edu.pw.elka.sag.*;
import pl.edu.pw.elka.sag.constants.*;
import pl.edu.pw.elka.sag.entities.*;

public class ServeMovementInfoBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -7164928311123018886L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.MOVEMENT_INFO_CONVERSATION_TYPE);
	
	private final IMovable movable;

	public ServeMovementInfoBehaviour(IMovable movable)
	{
		super();
		this.movable = movable;
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
				reply.setContentObject(new MovementInfo(movable.getLocation(), movable.getDirection()));
				
				myAgent.send(reply);
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
