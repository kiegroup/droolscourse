package droolscours;

import droolscours.util.OutputDisplay;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import util.KnowledgeSessionHelper;


public class Testlesson31 {
    static KieContainer kieContainer;
    StatelessKieSession sessionStateless = null;
    KieSession sessionStatefull = null;

    @BeforeClass
    public static void beforeClass() {
        kieContainer = KnowledgeSessionHelper.createRuleBase();
    }

    @Test
    public void testInConstrait() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson31-session");
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("showResult", display);
        CashFlow cashFlow = new CashFlow();
        cashFlow.setType(CashFlow.CREDIT);
        sessionStatefull.insert(cashFlow);
        sessionStatefull.fireAllRules();
    }

    @Test
    public void testNestedAccessor() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson31-session");
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("showResult", display);
        Customer customer = new Customer();
        customer.setName("Héron");
        customer.setSurname("Nicolas");
        PrivateAccount pAccount = new PrivateAccount();
        pAccount.setOwner(customer);
        sessionStatefull.insert(pAccount);
        sessionStatefull.fireAllRules();
    }

    @Test
    public void testInOrFact() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson31-session");
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("showResult", display);
        Customer customer = new Customer();

        customer.setCountry("GB");
        sessionStatefull.insert(customer);
        PrivateAccount pAccount = new PrivateAccount();
        pAccount.setOwner(customer);
        sessionStatefull.insert(pAccount);
        sessionStatefull.fireAllRules();
    }

    @Test
    public void testNotCondition() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson31-session");
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("showResult", display);
        sessionStatefull.fireAllRules();
    }

    @Test
    public void testExistsCondition() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "lesson31-session");
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("showResult", display);
        Account pAccount = new Account();
        sessionStatefull.insert(pAccount);
        sessionStatefull.fireAllRules();
    }

    @Test
    public void testForALl() throws Exception {
        sessionStatefull = KnowledgeSessionHelper
                .getStatefulKnowledgeSessionWithCallback(kieContainer, "ksession-lesson3");
        OutputDisplay display = new OutputDisplay();
        sessionStatefull.setGlobal("showResult", display);
        Account a = new Account();
        a.setAccountno(1);
        a.setBalance(0);
        sessionStatefull.insert(a);
        CashFlow cash1 = new CashFlow();
        cash1.setAccountNo(1);


        sessionStatefull.insert(cash1);
        CashFlow cash2 = new CashFlow();
        cash2.setAccountNo(1);

        sessionStatefull.insert(cash2);
        Account a2 = new Account();
        a2.setAccountno(2);
        a2.setBalance(0);
        sessionStatefull.insert(a2);
        CashFlow cash3 = new CashFlow();
        cash3.setAccountNo(2);

        sessionStatefull.insert(cash3);

        sessionStatefull.fireAllRules();
    }


}
