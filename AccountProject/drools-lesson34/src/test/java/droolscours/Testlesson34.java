package droolscours;

import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import util.KnowledgeSessionHelper;


public class Testlesson34 {
    static KieContainer kieContainer;
    StatelessKieSession sessionStateless = null;
    KieSession sessionStatefull = null;

    @BeforeClass
    public static void beforeClass() {
        kieContainer = KnowledgeSessionHelper.createRuleBase();
    }

    @Test
    public void testUnFaitSansFait() {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSession(kieContainer, "lesson1-session");
        sessionStatefull.fireAllRules();
        System.out.println("Did you see something ?");
    }

}
