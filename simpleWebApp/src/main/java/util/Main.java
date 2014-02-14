package util;

import droolscours.loyalty.ServiceCalculate;
import droolscours.loyalty.domains.Product;
import droolscours.loyalty.domains.Ticket;
import droolscours.loyalty.domains.TicketLine;

/**
 * Created by IntelliJ IDEA.
 * Date: 14/02/14
 * Time: 13:12
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String args[]) throws Exception{
        ServiceCalculate serviceCalculate= new ServiceCalculate();
        Ticket ticket = new Ticket();
        ticket.setDateTicket(DateHelper.getDate("2012-01-01"));
        TicketLine ticketLine = new TicketLine();

        ticket.getTicketLines().add(ticketLine);
        ticketLine.setLineTotal(new Float("2.0"));
        Product product = new Product();
        product.setName("pampers");
        ticketLine.setProduct(product);
        serviceCalculate.calculate(ticket);

    }
}
