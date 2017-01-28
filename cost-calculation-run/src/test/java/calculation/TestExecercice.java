package calculation;

import cost.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import util.KnowledgeSessionHelper;

@SuppressWarnings("restriction")
public class TestExecercice {

    static KieContainer kieContainer;
    StatelessKieSession sessionStateless = null;
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
        System.out.println("------------After------------");
    }

    private void insertIntoSession(KieSession sessionStatefull, CostCalculationRequest request) {
        sessionStatefull.insert(request);
        if (request.getOrder() != null) {
            sessionStatefull.insert(request.getOrder());
            for (OrderLine orderLine : request.getOrder().getOrderLines()) {
                sessionStatefull.insert(orderLine);
                sessionStatefull.insert(orderLine.getProduct());
            }
        }
        if (request.getTrip() != null) {
            sessionStatefull.insert(request.getTrip());
            for (Step step : request.getTrip().getSteps()) {
                sessionStatefull.insert(step);
                sessionStatefull.insert(step.getStepStart());
                sessionStatefull.insert(step.getStepEnd());
            }
        }
    }

    @Test
    public void testFirstOne() {
        sessionStatefull = KnowledgeSessionHelper.getStatefulKnowledgeSessionForJBPM(kieContainer, "ksession4");
        City cityOfShangai = new City(City.ShangaiCityName);
        City cityOfRotterdam = new City(City.RotterdamCityName);
        City cityOfTournai = new City(City.TournaiCityName);
        City cityOfLille = new City(City.LilleCityName);
        Step step1 = new Step(cityOfShangai, cityOfRotterdam, 22000, Step.Ship_TransportType);
        Step step2 = new Step(cityOfRotterdam, cityOfTournai, 300, Step.train_TransportType);
        Step step3 = new Step(cityOfTournai, cityOfLille, 20, Step.truck_TransportType);
        Trip myTrip = new Trip("trip1");
        myTrip.getSteps().add(step1);
        myTrip.getSteps().add(step2);
        myTrip.getSteps().add(step3);
        CostCalculationRequest request = new CostCalculationRequest();
        request.setTrip(myTrip);
        Product drillProduct = new Product("Drill", 0.2, 0.4, 0.3, 2, Product.transportType_pallet);
        Product screwDriverProduct = new Product("Screwdriver", 0.03, 0.02, 0.2, 0.2, Product.transportType_pallet);
        Product sandProduct = new Product("Sand", 0.0, 0.0, 0.0, 0.0, Product.transportType_bulkt);
        Product gravelProduct = new Product("Gravel", 0.0, 0.0, 0.0, 0.0, Product.transportType_bulkt);
        Product furnitureProduct = new Product("Furniture", 0.0, 0.0, 0.0, 0.0, Product.transportType_individual);
        Order myOrder = new Order("1");

        myOrder.getOrderLines().add(new OrderLine(1000, drillProduct));
        myOrder.getOrderLines().add(new OrderLine(1000, screwDriverProduct));
        myOrder.getOrderLines().add(new OrderLine(35000.0, sandProduct));
        myOrder.getOrderLines().add(new OrderLine(14000.0, gravelProduct));
        myOrder.getOrderLines().add(new OrderLine(500, furnitureProduct));
        request.setOrder(myOrder);
        long before = System.currentTimeMillis();

        this.insertIntoSession(sessionStatefull, request);
        sessionStatefull.startProcess("P1");
        int i = sessionStatefull.fireAllRules();
        long after = System.currentTimeMillis();
        System.out.println("NumberRules Executed " + i);
        System.out.println("Rules executed in " + (after - before) + " ms");
        Double dd = new Double(i) / (after - before) * 1000;
        System.out.println("NbreRules/seconde=" + dd);

    }

}
