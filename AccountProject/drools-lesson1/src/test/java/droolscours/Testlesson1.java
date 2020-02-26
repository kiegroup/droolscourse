package droolscours;

import droolscours.util.OutputDisplay;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;
import util.KnowledgeSessionHelper;


public class Testlesson1 {
    static KieContainer kieContainer;
    StatelessKieSession sessionStateless = null;
    KieSession sessionStatefull = null;

    @BeforeClass
    public static void beforeClass() {
        kieContainer = KnowledgeSessionHelper.createRuleBase();
    }
    
    @Before
    public void setUp() {
        System.out.println("-----------Before-------------");
    }
    @After
    public void tearDown() {
        System.out.println("-----------After-----------");
    }

    @Test
    public void testRuleOneFactWithoutFact() {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSession(kieContainer, "lesson1-session");
        sessionStatefull.fireAllRules();
        System.out.println("Did you see something ?");
    }

    @Test
    public void testRuleOneFactWithFact() {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSession(kieContainer, "lesson1-session");
       
        Account a = new Account();
        sessionStatefull.insert(a);
        sessionStatefull.fireAllRules();
        
        System.out.println("So you saw something ;)");
    }

    @Test
    public void testRuleOneFactWithFactAndUsageOfGlobal() {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSession(kieContainer, "lesson1-session");
        Customer a = new Customer();
        OutputDisplay display = new OutputDisplay();

        sessionStatefull.setGlobal("showResult", display);
        sessionStatefull.insert(a);
        sessionStatefull.fireAllRules();
        System.out.println("So you saw something ;)");
    }


    @Test
    public void testRuleOneFactWithFactAndUsageOfGlobalAndCallBack() {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSession(kieContainer, "lesson1-session");
        sessionStatefull.addEventListener(new RuleRuntimeEventListener() {
            public void objectInserted(ObjectInsertedEvent event) {
                System.out.println("Object inserted \n"
                        + event.getObject().toString());
            }

            public void objectUpdated(ObjectUpdatedEvent event) {
                System.out.println("Object was updated \n"
                        + "new Content \n" + event.getObject().toString());
            }

            public void objectDeleted(ObjectDeletedEvent event) {
                System.out.println("Object retracted \n"
                        + event.getOldObject().toString());
            }
        });
        Customer a = new Customer();
        a.setName("Heron");
        FactHandle handlea = sessionStatefull.insert(a);
        a.setSurname("Nicolas");
        sessionStatefull.update(handlea, a);
        sessionStatefull.delete(handlea);
        sessionStatefull.fireAllRules();
        System.out.println("So you saw something ;)");
    }

    @Test
    public void testRuleOneFactFireConditionStep1() {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSession(kieContainer, "lesson1-session");
        Account a = new Account();
        FactHandle handlea = sessionStatefull.insert(a);
        System.out.println("First fireAllRules");
        sessionStatefull.fireAllRules();
        System.out.println("Second fireAllRules");
        sessionStatefull.fireAllRules();
        System.out.println("Did A rule be fired");
    }

    @Test
    public void testRuleOneFactFireConditionStep2() {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSession(kieContainer, "lesson1-session");
        Account a = new Account();
        FactHandle handlea = sessionStatefull.insert(a);
        System.out.println("First fireAllRules");
        sessionStatefull.fireAllRules();
        System.out.println("Updating fact");
        sessionStatefull.update(handlea, a);
        System.out.println("Second fireAllRules");
        sessionStatefull.fireAllRules();
        System.out.println("Did A rule be fired");
    }

    @Test
    public void testRuleOneFactThatInsertObject() {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSession(kieContainer, "lesson1-session");

        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("showResult", display);

        sessionStatefull.addEventListener(new RuleRuntimeEventListener() {
            public void objectInserted(ObjectInsertedEvent event) {
                System.out.println("Object inserted \n"
                        + event.getObject().toString());
            }

            public void objectUpdated(ObjectUpdatedEvent event) {
                System.out.println("Object was updated \n"
                        + "new Content \n" + event.getObject().toString());
            }

            public void objectDeleted(ObjectDeletedEvent event) {
                System.out.println("Object retracted \n"
                        + event.getOldObject().toString());
            }
        });
        CashFlow a = new CashFlow();
        FactHandle handlea = sessionStatefull.insert(a);
        sessionStatefull.fireAllRules();
    }

    @Test
    public void testRuleOneFactWithFactAndAllCallbacks() {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson1-session");
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("showResult", display);
        Account a = new Account();
        sessionStatefull.insert(a);
        sessionStatefull.fireAllRules();
        CashFlow b = new CashFlow();
        FactHandle handlea = sessionStatefull.insert(b);
        sessionStatefull.fireAllRules();
        System.out.println("So you saw something ;)");
    }

}
