package droolscours;

import org.drools.runtime.StatefulKnowledgeSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.KnowledgeSessionHelper;
import util.OutputDisplay;


public class TestRuleFlow {
	StatefulKnowledgeSession sessionStatefull = null;

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
	     .getStatefulKnowledgeSession("demo-ruleflow.drl","RuleFlow1.rf");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
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
	     .getStatefulKnowledgeSession("demo-ruleflow2.drl","RuleFlow2.rf");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		Account a = new Account();
		sessionStatefull.insert(a);
		AccountingPeriod period = new AccountingPeriod();
		sessionStatefull.insert(period);
		sessionStatefull.fireAllRules();
	}
	@Test
	public void testdeuxFait3() {
		sessionStatefull = KnowledgeSessionHelper
	     .getStatefulKnowledgeSession("demo-ruleflow3.drl","RuleFlow3.rf");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		Account a = new Account();
		a.setBalance(1500);
		sessionStatefull.insert(a);
		AccountingPeriod period = new AccountingPeriod();
		sessionStatefull.insert(period);
		sessionStatefull.fireAllRules();
	}

}
