package util;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.event.rule.DefaultAgendaEventListener;
import org.drools.event.rule.ActivationCancelledEvent;
import org.drools.event.rule.ActivationCreatedEvent;
import org.drools.event.rule.AfterActivationFiredEvent;
import org.drools.event.rule.AgendaGroupPoppedEvent;
import org.drools.event.rule.AgendaGroupPushedEvent;
import org.drools.event.rule.BeforeActivationFiredEvent;
import org.drools.event.rule.ObjectInsertedEvent;
import org.drools.event.rule.ObjectRetractedEvent;
import org.drools.event.rule.ObjectUpdatedEvent;
import org.drools.event.rule.WorkingMemoryEventListener;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;

public class KnowledgeSessionHelper {

	private static KnowledgeBase createRuleBase(String drlFile) {
		KnowledgeBase ruleBase = null;
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource(drlFile),
				ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException(
					"Probleme a la lecture du fichier.");
		}
		ruleBase = KnowledgeBaseFactory.newKnowledgeBase();
		ruleBase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return ruleBase;
	}

	public static StatelessKnowledgeSession getStatelessKnowledgeSession(
			String drlFile) {
		KnowledgeBase ruleBase = createRuleBase(drlFile);
		return ruleBase.newStatelessKnowledgeSession();
	}

	public static StatefulKnowledgeSession getStatefulKnowledgeSession(
			String drlFile) {
		KnowledgeBase ruleBase = createRuleBase(drlFile);
		return ruleBase.newStatefulKnowledgeSession();
	}

	public static StatefulKnowledgeSession getStatefulKnowledgeSessionWithCallback(
			String drlFile) {
		StatefulKnowledgeSession session = getStatefulKnowledgeSession(drlFile);
		session.addEventListener(new WorkingMemoryEventListener() {
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
		session.addEventListener(new DefaultAgendaEventListener() {
			@Override
			public void beforeActivationFired(BeforeActivationFiredEvent arg0) {
				System.out.println("La règle "
						+ arg0.getActivation().getRule().getName()
						+ " va être déclenchée");
			}

			@Override
			public void agendaGroupPushed(AgendaGroupPushedEvent arg0) {
			}

			@Override
			public void agendaGroupPopped(AgendaGroupPoppedEvent arg0) {
			}

			@Override
			public void afterActivationFired(AfterActivationFiredEvent arg0) {
				System.out.println("La règle "
						+ arg0.getActivation().getRule().getName()
						+ " a êté déclenchée");
			}

			@Override
			public void activationCreated(ActivationCreatedEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void activationCancelled(ActivationCancelledEvent arg0) {
			}
		});
		return session;
	}
	
	public static StatefulKnowledgeSession getStatefulKnowledgeSession(
			String drlFile,String rfFile) {
		KnowledgeBase ruleBase = null;
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory
				.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource(drlFile),
				ResourceType.DRL);
		kbuilder.add(ResourceFactory.newClassPathResource(rfFile),
				ResourceType.DRF);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error : errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException(
					"Probleme a la lecture du fichier.");
		}
		ruleBase = KnowledgeBaseFactory.newKnowledgeBase();
		ruleBase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return ruleBase.newStatefulKnowledgeSession();
	}

}
