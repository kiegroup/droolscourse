package util;

import org.chtijbug.drools.runtime.DroolsChtijbugException;
import org.chtijbug.drools.runtime.RuleBasePackage;
import org.chtijbug.drools.runtime.impl.RuleBaseSingleton;
import org.chtijbug.drools.runtime.resource.Bpmn2DroolsRessource;
import org.chtijbug.drools.runtime.resource.DrlDroolsRessource;
import org.chtijbug.drools.runtime.resource.DroolsResource;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.*;
import org.drools.event.rule.*;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.StatelessKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyKnowledgeSessionHelper {
    private static KnowledgeBase ruleBase = null;
    /**
     * Class Logger
     */
    private static Logger logger = LoggerFactory.getLogger(MyKnowledgeSessionHelper.class);

    public static RuleBasePackage getRuleBasePackage(String... filenames) throws DroolsChtijbugException {
        logger.debug(">>createPackageBasePackage");
        CoursHistoryContainer coursHistoryContainer = new CoursHistoryContainer();
        RuleBasePackage ruleBasePackage = new RuleBaseSingleton(RuleBaseSingleton.DEFAULT_RULE_THRESHOLD, coursHistoryContainer);
        try {
            for (String filename : filenames) {
                String extensionName = MyKnowledgeSessionHelper.getFileExtension(filename);
                DroolsResource resource = null;
                if ("DRL".equals(extensionName)) {
                    resource = DrlDroolsRessource.createClassPathResource(filename);
                } else if ("BPMN2".equals(extensionName)) {
                    resource = Bpmn2DroolsRessource.createClassPathResource(filename);
                }
                if (resource != null) {
                    ruleBasePackage.addDroolsResouce(resource);
                } else {
                    throw new DroolsChtijbugException(DroolsChtijbugException.UnknowFileExtension, filename, null);
                }
            }
            ruleBasePackage.createKBase();
            // this.ruleBasePackage = ruleBasePackage;
            //_____ Returning the result
            return ruleBasePackage;
        } finally {
            logger.debug("<<createPackageBasePackage", ruleBasePackage);
        }
    }

    private static String getFileExtension(String ressourceName) {
        int mid = ressourceName.lastIndexOf(".");
        String ext = ressourceName.substring(mid + 1, ressourceName.length()).toUpperCase();
        return ext;
    }

    private static KnowledgeBase createRuleBase(String drlFile) {

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
        if (ruleBase == null) {
            ruleBase = createRuleBase(drlFile);
        }
        return ruleBase.newStatelessKnowledgeSession();
    }

    public static StatefulKnowledgeSession getStatefulKnowledgeSession(
            String drlFile) {
        if (ruleBase == null) {
            ruleBase = createRuleBase(drlFile);
        }
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
            String drlFile, String rfFile) {
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
