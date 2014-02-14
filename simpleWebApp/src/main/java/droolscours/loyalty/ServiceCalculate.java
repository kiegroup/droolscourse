package droolscours.loyalty;

import droolscours.loyalty.domains.Ticket;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.RuleBaseBuilder;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CoursHistoryContainer;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(endpointInterface = "droolscours.loyalty.IServiceCalculate")
public class ServiceCalculate implements IServiceCalculate {

	/*
     * (non-Javadoc)
	 * 
	 * @see
	 * droolscours.loyalty.IServiceCalculate#calculate(droolscours.loyalty.domains
	 * .Ticket)
	 */
    /**
     * Class Logger
     */
    private static Logger logger = LoggerFactory.getLogger(ServiceCalculate.class);


    private RuleBasePackage ruleBasePackage = null;

    @Override
    public Ticket calculate(@WebParam(name = "ticket") Ticket ticket) {
        if (ruleBasePackage == null) {

            try {
                CoursHistoryContainer coursHistoryContainer = new CoursHistoryContainer();
                ruleBasePackage= RuleBaseBuilder.createPackageBasePackageWithListener(coursHistoryContainer,"File1.drl");
            } catch (DroolsChtijbugException e) {
                logger.error("Could not create RuleBase", e);
            }
        }
        RuleBaseSession sessionStatefull = null;
        try {
            sessionStatefull = ruleBasePackage.createRuleBaseSession();
            sessionStatefull.insertByReflection(ticket);
            sessionStatefull.fireAllRules();
//            System.out.println(sessionStatefull.getHistoryContainer().getListHistoryEvent().toString());
        } catch (DroolsChtijbugException e) {
            logger.error("Error in fireallrules", e);
        }

        return ticket;
    }
}
