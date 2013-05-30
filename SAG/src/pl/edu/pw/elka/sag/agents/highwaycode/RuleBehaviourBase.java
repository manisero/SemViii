package pl.edu.pw.elka.sag.agents.highwaycode;

import java.io.*;

import jade.core.*;
import jade.core.behaviours.*;
import jade.lang.acl.*;
import pl.edu.pw.elka.sag.logic.highwaycode.*;

public abstract class RuleBehaviourBase extends CyclicBehaviour
{
	private static final long serialVersionUID = -1063042687412453068L;
	
	private final MessageTemplate messageTemplate;
	protected final IHighwayCode highwayCode;
	
	protected RuleBehaviourBase(Agent agent, IHighwayCode highwayCode, String conversationType)
	{
		super(agent);
		messageTemplate = MessageTemplate.MatchConversationId(conversationType);
		this.highwayCode = highwayCode;
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
				fillReply(message, reply);
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
	
	protected abstract void fillReply(ACLMessage message, ACLMessage reply) throws UnreadableException, IOException;
}
