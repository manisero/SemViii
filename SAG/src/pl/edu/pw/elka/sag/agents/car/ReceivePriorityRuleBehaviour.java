package pl.edu.pw.elka.sag.agents.car;

import pl.edu.pw.elka.sag.constants.ConversationTypes;
import pl.edu.pw.elka.sag.ontology.concepts.CarMovementInfo;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceivePriorityRuleBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -445730167222694985L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.PRIORITY_RULE_CONVERSATION_TYPE);

	private CarMovementInfo movementInfo;
	
	public ReceivePriorityRuleBehaviour(CarAgent agent, CarMovementInfo movementInfo)
	{
		super(agent);
		this.movementInfo = movementInfo;
	}
	
	@Override
	public void action()
	{
		ACLMessage message = myAgent.receive(messageTemplate);
		
		if (message != null)
		{
			if (message.getPerformative() == ACLMessage.REFUSE)
			{
				movementInfo.setHasPriority(false);
			}
		}
		else
		{
			block();
		}
	}
}
