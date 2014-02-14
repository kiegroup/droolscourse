package loyalty;

import loyalty.domains.Ticket;
import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.RuleBaseBuilder;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.RuleBaseSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.CoursHistoryContainer;

public class DroolsServiceCalculate implements IDroolsServiceCalculate {

	/*
     * (non-Javadoc)
	 * 
	 * @see
	 * IDroolsServiceCalculate#calculate(droolscours.loyalty.domains
	 * .Ticket)
	 */
    /**
     * Class Logger
     */
    private static Logger logger = LoggerFactory.getLogger(DroolsServiceCalculate.class);


    private RuleBasePackage ruleBasePackage = null;

    @Override
    public Ticket calculate( Ticket ticket) {
        if (ruleBasePackage == null) {

            try {
                CoursHistoryContainer coursHistoryContainer = new CoursHistoryContainer();
                ruleBasePackage= RuleBaseBuilder.createPackageBasePackageWithListener(coursHistoryContainer, "File1.drl");
                //ruleBasePackage= RuleBaseBuilder.createGuvnorRuleBasePackageWithListener(coursHistoryContainer,"http://localhost:8080", "drools-guvnor", "mypackage","1.27.0-PROD",
                                                                                         // "admin", "admin");
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
