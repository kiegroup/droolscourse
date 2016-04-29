package droolscours;

import droolscours.service.CustomerService;
import junit.framework.Assert;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.DateHelper;
import util.KnowledgeSessionHelper;
import util.OutputDisplay;


public class Testlanguage {
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
	     .getStatefulKnowledgeSessionWithCallback("demo-Language.drl");

		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		Account a = new Account();
		sessionStatefull.insert(a);
		AccountingPeriod period = new AccountingPeriod();
		sessionStatefull.insert(period);
		sessionStatefull.fireAllRules();
	}

	@Test
	public void testdeuxFait2() throws Exception {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSessionWithCallback("demo-Language.drl");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		Account a = new Account();
		a.setAccountno(1);
		a.setBalance(0);
		sessionStatefull.insert(a);
		CashFlow cash1 = new CashFlow();
		cash1.setAccountNo(1);
		cash1.setAmount(1000);
		cash1.setMvtDate(DateHelper.getDate("2010-01-15"));
		cash1.setType(CashFlow.CREDIT);
		sessionStatefull.insert(cash1);
		sessionStatefull.fireAllRules();
		Assert.assertTrue(a.getBalance()==1000);
	}
	@Test
	public void testdeuxFait3() throws Exception {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSessionWithCallback("demo-Language.drl");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		Account a = new Account();
		a.setAccountno(1);
		a.setBalance(0);
		sessionStatefull.insert(a);
		CashFlow cash1 = new CashFlow();
		cash1.setAccountNo(1);
		cash1.setAmount(1000);
		cash1.setMvtDate(DateHelper.getDate("2010-01-15"));
		cash1.setType(CashFlow.CREDIT);
		sessionStatefull.insert(cash1);
		CashFlow cash2 = new CashFlow();
		cash2.setAccountNo(2);
		cash2.setAmount(1000);
		cash2.setMvtDate(DateHelper.getDate("2010-01-15"));
		cash2.setType(CashFlow.CREDIT);
		sessionStatefull.insert(cash2);
		sessionStatefull.fireAllRules();
		Assert.assertTrue(a.getBalance()==1000);
	}
	@Test
	public void testdeuxFait4() throws Exception {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSessionWithCallback("demo-Language2.drl");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		Account a = new Account();
		a.setAccountno(1);
		a.setBalance(0);
		sessionStatefull.insert(a);
		CashFlow cash1 = new CashFlow();
		cash1.setAccountNo(1);
		cash1.setAmount(1000);
		cash1.setMvtDate(DateHelper.getDate("2010-01-15"));
		cash1.setType(CashFlow.CREDIT);
		sessionStatefull.insert(cash1);
		CashFlow cash2 = new CashFlow();
		cash2.setAccountNo(1);
		cash2.setAmount(500);
		cash2.setMvtDate(DateHelper.getDate("2010-02-15"));
		cash2.setType(CashFlow.DEBIT);
		sessionStatefull.insert(cash2);
		CashFlow cash3 = new CashFlow();
		cash3.setAccountNo(1);
		cash3.setAmount(1000);
		cash3.setMvtDate(DateHelper.getDate("2010-04-15"));
		cash3.setType(CashFlow.CREDIT);
		sessionStatefull.insert(cash3);
		AccountingPeriod period = new AccountingPeriod();
		period.setStartDate(DateHelper.getDate("2010-01-01"));
		period.setEndDate(DateHelper.getDate("2010-03-31"));
		sessionStatefull.insert(period);
		sessionStatefull.fireAllRules();
		Assert.assertTrue(a.getBalance()==500);
	}
	@Test
	public void testdeuxFait5() throws Exception {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSessionWithCallback("demo-Language3.drl");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		Customer customer = new Customer();
		customer.setName("Héron");
		customer.setSurname("Nicolas");
		PrivateAccount pAccount = new PrivateAccount();
		pAccount.setOwner(customer);
		sessionStatefull.insert(pAccount);	
		sessionStatefull.fireAllRules();
	}
	@Test
	public void testdeuxFait6() throws Exception {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSessionWithCallback("demo-Language3.drl");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		Customer customer = new Customer();
		customer.setName("Héron");
		customer.setSurname("Nicolas");
		customer.setCountry("GB");
		sessionStatefull.insert(customer);
		PrivateAccount pAccount = new PrivateAccount();
		pAccount.setOwner(customer);
		sessionStatefull.insert(pAccount);	
		sessionStatefull.fireAllRules();
	}
	@Test
	public void testdeuxFait7() throws Exception {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSessionWithCallback("demo-Language3.drl");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		Account pAccount = new Account();
		sessionStatefull.insert(pAccount);	
		sessionStatefull.fireAllRules();
	}
	@Test
	public void testdeuxFait8() throws Exception {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSessionWithCallback("demo-Language3.drl");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		Account pAccount = new Account();
		sessionStatefull.insert(pAccount);	
		sessionStatefull.fireAllRules();
	}
	@Test
	public void testdeuxFait9() throws Exception {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSessionWithCallback("demo-Language4.drl");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		Account a = new Account();
		a.setAccountno(1);
		a.setBalance(0);
		sessionStatefull.insert(a);
		CashFlow cash1 = new CashFlow();
		cash1.setAccountNo(1);
		cash1.setAmount(1000);
		cash1.setMvtDate(DateHelper.getDate("2010-01-15"));
		cash1.setType(CashFlow.CREDIT);
		sessionStatefull.insert(cash1);
		CashFlow cash2 = new CashFlow();
		cash2.setAccountNo(1);
		cash2.setAmount(500);
		cash2.setMvtDate(DateHelper.getDate("2010-02-15"));
		cash2.setType(CashFlow.DEBIT);
		sessionStatefull.insert(cash2);
		Account a2 = new Account();
				a2.setAccountno(2);
				a2.setBalance(0);
				sessionStatefull.insert(a2);
		CashFlow cash3 = new CashFlow();
		cash3.setAccountNo(2);
		cash3.setAmount(1000);
		cash3.setMvtDate(DateHelper.getDate("2010-04-15"));
		cash3.setType(CashFlow.CREDIT);
		sessionStatefull.insert(cash3);

		sessionStatefull.fireAllRules();
	}
	@Test
	public void testdeuxFait10() throws Exception {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSessionWithCallback("demo-Language5.drl");	
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		sessionStatefull.setGlobal("serviceCustomer", new CustomerService());
		Customer c = new Customer("Héron","Nicolas","A");
		sessionStatefull.insert(c);
		sessionStatefull.fireAllRules();
	}
	@Test
	public void testdeuxFait11() throws Exception {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSessionWithCallback("demo-Language6.drl");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		Account a = new Account();
		a.setAccountno(1);
		a.setBalance(0);
		sessionStatefull.insert(a);
		sessionStatefull.insert(new CashFlow(DateHelper.getDate("2010-01-15"),1000,CashFlow.CREDIT,1));
		sessionStatefull.insert(new CashFlow(DateHelper.getDate("2010-02-15"),500,CashFlow.DEBIT,1));
		sessionStatefull.insert(new CashFlow(DateHelper.getDate("2010-04-15"),1000,CashFlow.CREDIT,1));
		sessionStatefull.insert(new AccountingPeriod(DateHelper.getDate("2010-01-01"),DateHelper.getDate("2010-31-31")));
		sessionStatefull.fireAllRules();
	}
	@Test
	public void testdeuxFait12() throws Exception {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSessionWithCallback("demo-Language7.drl");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		sessionStatefull.insert(new Account(1,0));
		FactHandle fa = sessionStatefull.insert(new CashFlow(DateHelper.getDate("2010-01-15"), 1000, CashFlow.CREDIT, 1));
		sessionStatefull.insert(new CashFlow(DateHelper.getDate("2010-02-15"),500,CashFlow.DEBIT,1));
		sessionStatefull.insert(new CashFlow(DateHelper.getDate("2010-04-15"),1000,CashFlow.CREDIT,1));
		sessionStatefull.insert(new AccountingPeriod(DateHelper.getDate("2010-01-01"),DateHelper.getDate("2010-12-31")));
		sessionStatefull.fireAllRules();
		sessionStatefull.retract(fa);
		sessionStatefull.fireAllRules();
	}
}
