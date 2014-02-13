package droolscours.loyalty;

import droolscours.loyalty.domains.Ticket;
import org.drools.runtime.StatefulKnowledgeSession;
import util.KnowledgeSessionHelper;

import javax.jws.WebService;

@WebService(endpointInterface = "droolscours.loyalty.IServiceCalculate")
public class ServiceCalculate  implements IServiceCalculate{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * droolscours.loyalty.IServiceCalculate#calculate(droolscours.loyalty.domains
	 * .Ticket)
	 */
	private StatefulKnowledgeSession sessionStatefull = null;


    @Override
	public Ticket calculate( Ticket ticket) {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSession("File1.drl");
		sessionStatefull.insert(ticket);
		sessionStatefull.fireAllRules();
		System.out.println("Works");
		return ticket;
	}
}
