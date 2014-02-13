package service;

import java.util.List;

import demo.Product;
import demo.Ticket;
import demo.TicketLine;

public class TickerService {
	public void CalculateAmountTicket(Ticket ticket) {
		if (ticket != null) {
			Float total = new Float(0.0);
			for (TicketLine line : ticket.getTicketLines()) {
				total = total + line.getQuantity() * line.getPrice();
			}
			ticket.setAmount(total);
		}
	}

	public void CalculateTicketLineAmount(TicketLine line) {
		if (line != null) {
			Float total = new Float(0.0);
			total = line.getQuantity() * line.getPrice();
			line.setLineTotal(total);
		}
	}

	public void CalculateTicketLinesAmount(List<TicketLine> lines) {
		if (lines != null) {
			Float total = new Float(0.0);
			for (TicketLine aLine : lines) {
				total = aLine.getQuantity() * aLine.getPrice();
				aLine.setLineTotal(total);
			}
		}
	}
	public void addProductTicket(Product p, TicketLine t){
		t.setProduct(p);
	}
}
