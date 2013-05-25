package pl.edu.pw.elka.sag.agents;

import jade.core.behaviours.*;
import jade.lang.acl.*;

import java.io.*;

import pl.edu.pw.elka.sag.*;
import pl.edu.pw.elka.sag.constants.*;

public class ServeLocationBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -624286605302240972L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.LOCATION_CONVERSATION_TYPE);
	
	private final ILocatable locatable;

	public ServeLocationBehaviour(ILocatable locatable)
	{
		this.locatable = locatable;
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
				reply.setContentObject(locatable.getLocation());
				
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
