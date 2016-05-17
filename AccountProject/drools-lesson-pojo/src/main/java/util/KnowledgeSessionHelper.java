package util;

import org.kie.api.KieServices;
import org.kie.api.event.rule.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

public class KnowledgeSessionHelper {

    public static KieContainer createRuleBase() {

        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        return kieContainer;
    }

    public static StatelessKieSession getStatelessKnowledgeSession(KieContainer kieContainer, String sessionName) {
        StatelessKieSession kSession = kieContainer.newStatelessKieSession(sessionName);

        return kSession;
    }

    public static KieSession getStatefulKnowledgeSession(KieContainer kieContainer, String sessionName) {
        KieSession kSession = kieContainer.newKieSession(sessionName);

        return kSession;
    }

    public static KieSession getStatefulKnowledgeSessionWithCallback(
            KieContainer kieContainer, String sessionName) {
        KieSession session = getStatefulKnowledgeSession(kieContainer, sessionName);
        session.addEventListener(new RuleRuntimeEventListener() {
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

        session.addEventListener(new AgendaEventListener() {
            public void matchCreated(MatchCreatedEvent event) {
                System.out.println("The rule "
                        + event.getMatch().getRule().getName()
                        + " can be fired in agenda");
            }

            public void matchCancelled(MatchCancelledEvent event) {
                System.out.println("The rule "
                        + event.getMatch().getRule().getName()
                        + " cannot b in agenda");
            }

            public void beforeMatchFired(BeforeMatchFiredEvent event) {
                System.out.println("The rule "
                        + event.getMatch().getRule().getName()
                        + " will be fired");
            }

            public void afterMatchFired(AfterMatchFiredEvent event) {
                System.out.println("The rule "
                        + event.getMatch().getRule().getName()
                        + " has be fired");
            }

            public void agendaGroupPopped(AgendaGroupPoppedEvent event) {

            }

            public void agendaGroupPushed(AgendaGroupPushedEvent event) {

            }

            public void beforeRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

            }

            public void afterRuleFlowGroupActivated(RuleFlowGroupActivatedEvent event) {

            }

            public void beforeRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

            }

            public void afterRuleFlowGroupDeactivated(RuleFlowGroupDeactivatedEvent event) {

            }
        });


        return session;
    }


}
