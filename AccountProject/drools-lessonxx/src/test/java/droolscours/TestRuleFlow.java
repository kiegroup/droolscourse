package droolscours;

import droolscours.util.OutputDisplay;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import util.KnowledgeSessionHelper;


public class TestRuleFlow {
	static KieContainer kieContainer;
	KieSession sessionStatefull = null;

	@BeforeClass
	public static void beforeClass() {
		kieContainer = KnowledgeSessionHelper.createRuleBase();
	}

	@Before
	public void setUp() throws Exception {
		System.out.println("------------Before------------");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("------------Après ------------");
	}

	@Test
	public void testdeuxFait1() {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSession(kieContainer, "demo-ruleflow1-rules");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("showResult", display);
		Account a = new Account();
		sessionStatefull.insert(a);
		AccountingPeriod period = new AccountingPeriod();
		sessionStatefull.insert(period);
		sessionStatefull.startProcess("RF1");
		sessionStatefull.fireAllRules();
	}
	@Test
	public void testdeuxFait2() {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSession(kieContainer, "demo-ruleflow2-rules");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("showResult", display);
		Account a = new Account();
		sessionStatefull.insert(a);
		AccountingPeriod period = new AccountingPeriod();
		sessionStatefull.insert(period);
		sessionStatefull.fireAllRules();
	}
	@Test
	public void testdeuxFait3() {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSession(kieContainer, "demo-ruleflow3-rules");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("showResult", display);
		Account a = new Account();
		a.setBalance(1500);
		sessionStatefull.insert(a);
		AccountingPeriod period = new AccountingPeriod();
		sessionStatefull.insert(period);
		
		sessionStatefull.fireAllRules();
		
	}

}
