package pl.edu.pw.elka.sag.agents.car;

import pl.edu.pw.elka.sag.constants.ConversationTypes;
import pl.edu.pw.elka.sag.ontology.concepts.CarMovementInfo;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveTypePriorityRuleBehaviour extends CyclicBehaviour
{
	private static final long serialVersionUID = -445730167222694985L;
	private static final MessageTemplate messageTemplate = MessageTemplate.MatchConversationId(ConversationTypes.TYPE_PRIORITY_RULE_CONVERSATION_TYPE);

	private CarMovementInfo movementInfo;
	
	public ReceiveTypePriorityRuleBehaviour(CarAgent agent, CarMovementInfo movementInfo)
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
			if (message.getPerformative() == ACLMessage.AGREE)
			{
				movementInfo.setHasTypePriority(true);
			}
		}
		else
		{
			block();
		}
	}
}
