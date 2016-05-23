package droolscours;

import droolscours.service.CustomerService;
import droolscours.util.OutputDisplay;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import util.KnowledgeSessionHelper;


public class Testlesson33 {
    static KieContainer kieContainer;
    StatelessKieSession sessionStateless = null;
    KieSession sessionStatefull = null;

    @BeforeClass
    public static void beforeClass() {
        kieContainer = KnowledgeSessionHelper.createRuleBase();
    }

    @Test
    public void testFromLHS() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson33-session");
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("showResult", display);
        sessionStatefull.setGlobal("serviceCustomer", new CustomerService());
        Customer c = new Customer("Héron", "Nicolas", "A");
        sessionStatefull.insert(c);
        sessionStatefull.fireAllRules();
    }

}
