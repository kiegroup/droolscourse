package droolscours;

import droolscours.util.OutputDisplay;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;
import util.DateHelper;
import util.KnowledgeSessionHelper;


public class Testlesson35 {
    static KieContainer kieContainer;
    StatelessKieSession sessionStateless = null;
    KieSession sessionStatefull = null;

    @BeforeClass
    public static void beforeClass() {
        kieContainer = KnowledgeSessionHelper.createRuleBase();
    }

    @Test
    public void testAccumulate() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson35-session");
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("showResult", display);
        sessionStatefull.insert(new Account(1, 0));

        FactHandle fa = sessionStatefull.insert(new CashFlow(DateHelper.getDate("2010-01-15"), 1000, CashFlow.CREDIT, 1));
        sessionStatefull.insert(new CashFlow(DateHelper.getDate("2010-02-15"), 500, CashFlow.DEBIT, 1));
        sessionStatefull.insert(new CashFlow(DateHelper.getDate("2010-04-15"), 1000, CashFlow.CREDIT, 1));
        sessionStatefull.insert(new AccountingPeriod(DateHelper.getDate("2010-01-01"), DateHelper.getDate("2010-12-31")));
        sessionStatefull.fireAllRules();
        sessionStatefull.delete(fa);
        sessionStatefull.fireAllRules();
    }

}
