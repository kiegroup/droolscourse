package droolscours;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.event.rule.ObjectDeletedEvent;
import org.kie.api.event.rule.ObjectInsertedEvent;
import org.kie.api.event.rule.ObjectUpdatedEvent;
import org.kie.api.event.rule.RuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import util.DateHelper;
import util.KnowledgeSessionHelper;



public class PremierEssai {
	static KieContainer kieContainer;
	StatelessKieSession sessionStateless = null;
	KieSession sessionStatefull = null;

	@BeforeClass
	public static void beforeClass() {
		kieContainer = KnowledgeSessionHelper.createRuleBase();
	}

	@Test
	public void testSimple() throws Exception {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSession(kieContainer, "simplesample-session");
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
		Account account = new Account();
		account.setAccountno(1);
		sessionStatefull.insert(account);
		AccountingPeriod period = new AccountingPeriod();
		period.setStartDate(DateHelper.getDate("2010-01-01"));
		period.setEndDate(DateHelper.getDate("2010-03-31"));
		sessionStatefull.insert(period);
		CashFlow action1 = new CashFlow();
		action1.setAccountNo(1);
		action1.setAmount(1000);
		action1.setType(CashFlow.CREDIT);
		action1.setMvtDate(DateHelper.getDate("2010-01-02"));
		sessionStatefull.insert(action1);
		sessionStatefull.fireAllRules();
		Assert.assertTrue(account.getBalance() == 1000);
	}
}
