package droolscours;

import org.drools.event.rule.ObjectInsertedEvent;
import org.drools.event.rule.ObjectRetractedEvent;
import org.drools.event.rule.ObjectUpdatedEvent;
import org.drools.event.rule.WorkingMemoryEventListener;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.rule.FactHandle;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import util.KnowledgeSessionHelper;
import util.OutputDisplay;

public class TestUnFait {
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
	public void testUnFaitSansFait() {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSession("demo.drl");
		sessionStatefull.fireAllRules();
		System.out.println("Avez-vous vu quelquechose ?");
	}

	@Test
	public void testUnFaitAvecAccountCommeFait() {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSession("demo.drl");
		Account a = new Account();
		sessionStatefull.insert(a);
		sessionStatefull.fireAllRules();
		System.out.println("Vous avez donc vu quelquechose ;)");
	}

	@Test
	public void testUnFaitAvecAccountCommeFaitGlobal() {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSession("demo-global.drl");
		Account a = new Account();
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		sessionStatefull.insert(a);

		sessionStatefull.fireAllRules();
		System.out.println("Vous avez donc vu quelquechose ;)");
	}

	@Test
	public void testUnFaitAvecAccountCommeFaitGlobalEtAvecCallBack() {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSession("demo.drl");
		sessionStatefull.addEventListener(new WorkingMemoryEventListener() {
			//@Override
			public void objectUpdated(ObjectUpdatedEvent arg0) {
				System.out.println("Object mise à jour \n"
						+ "Nouvelles valeurs \n" + arg0.getObject().toString());
			}

			//@Override
			public void objectRetracted(ObjectRetractedEvent arg0) {
				System.out.println("Object retiré \n"
						+ arg0.getOldObject().toString());
			}

			//@Override
			public void objectInserted(ObjectInsertedEvent arg0) {
				System.out.println("Object inséré \n"
						+ arg0.getObject().toString());
			}
		});
		Account a = new Account();
		a.setAccountno(0);
		FactHandle handlea = sessionStatefull.insert(a);
		a.setAccountno(100);
		sessionStatefull.update(handlea, a);
		sessionStatefull.retract(handlea);
		sessionStatefull.fireAllRules();
		System.out.println("Vous avez donc vu quelquechose ;)");
	}
	@Test
	public void testUnFaitAvecAccountRDGDeclenchement1() {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSession("demo.drl");
		Account a = new Account();
		FactHandle handlea = sessionStatefull.insert(a);
		sessionStatefull.fireAllRules();
		sessionStatefull.fireAllRules();
	}
	@Test
	public void testUnFaitAvecAccountRDGDeclenchement2() {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSession("demo.drl");
		Account a = new Account();
		FactHandle handlea = sessionStatefull.insert(a);
		sessionStatefull.fireAllRules();
		sessionStatefull.update(handlea, a);
		sessionStatefull.fireAllRules();
	}
	@Test
	public void testUnFaitAvecAccountInsert() {
		sessionStatefull = KnowledgeSessionHelper
				.getStatefulKnowledgeSession("demo-insertupdatedelete.drl");
		OutputDisplay display = new OutputDisplay();
		sessionStatefull.setGlobal("result", display);
		
		sessionStatefull.addEventListener(new WorkingMemoryEventListener() {
			//@Override
			public void objectUpdated(ObjectUpdatedEvent arg0) {
				System.out.println("Object mise à jour \n"
						+ "Nouvelles valeurs \n" + arg0.getObject().toString());
			}
			//@Override
			public void objectRetracted(ObjectRetractedEvent arg0) {
				System.out.println("Object retiré \n"
						+ arg0.getOldObject().toString());
			}
			//@Override
			public void objectInserted(ObjectInsertedEvent arg0) {
				System.out.println("Object inséré \n"
						+ arg0.getObject().toString());
			}
		});
		Account a = new Account();
		FactHandle handlea = sessionStatefull.insert(a);
		sessionStatefull.fireAllRules();
	}
}
