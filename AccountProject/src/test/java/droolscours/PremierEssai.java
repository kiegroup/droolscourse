package droolscours;

import org.drools.event.rule.ObjectInsertedEvent;
import org.drools.event.rule.ObjectRetractedEvent;
import org.drools.event.rule.ObjectUpdatedEvent;
import org.drools.event.rule.WorkingMemoryEventListener;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import org.junit.Assert;
import org.junit.Test;

import util.DateHelper;
import util.KnowledgeSessionHelper;



public class PremierEssai {
	StatelessKnowledgeSession sessionStateless = null;
	StatefulKnowledgeSession sessionStatefull = null;

	@Test
	public void test1() {
		sessionStateless = KnowledgeSessionHelper
				.getStatelessKnowledgeSession("Sample.drl");
		sessionStateless.execute(new String("Hello"));
	}

	@Test
	public void testSimple() throws Exception {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSession("SimpleSample.drl");
		sessionStatefull.addEventListener(new WorkingMemoryEventListener() {
			@Override
			public void objectUpdated(ObjectUpdatedEvent arg0) {
				System.out.println("Object mise à jour \n"
						+ "Nouvelles valeurs \n" + arg0.getObject().toString());
			}

			@Override
			public void objectRetracted(ObjectRetractedEvent arg0) {
				System.out.println("Object retiré \n"
						+ arg0.getOldObject().toString());
			}

			@Override
			public void objectInserted(ObjectInsertedEvent arg0) {
				System.out.println("Object inséré \n"
						+ arg0.getObject().toString());
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
