package loyalty.service;

import loyalty.DroolsServiceCalculate;
import loyalty.domains.Ticket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(endpointInterface = "loyalty.service.IServiceCalculate")
public class ServiceCalculate implements IServiceCalculate {

	/*
     * (non-Javadoc)
	 * 
	 * @see
	 * IDroolsServiceCalculate#calculate(droolscours.loyalty.domains
	 * .Ticket)
	 */
    /**
     * Class Logger
     */
    private static Logger logger = LoggerFactory.getLogger(DroolsServiceCalculate.class);




    private DroolsServiceCalculate droolsServiceCalculate;

    public void setDroolsServiceCalculate(DroolsServiceCalculate droolsServiceCalculate) {
        this.droolsServiceCalculate = droolsServiceCalculate;
    }

    @Override
    public Ticket calculate(@WebParam(name = "ticket") Ticket ticket) {

        droolsServiceCalculate.calculate(ticket);
        return ticket;
    }
}
