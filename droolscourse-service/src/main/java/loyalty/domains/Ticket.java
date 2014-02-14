package loyalty.domains;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ticket {

	private String Id;
	private Date dateTicket;
	private Float amount;
	private Card loyaltyCard;
	private Customer customer;
	
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Card getLoyaltyCard() {
		return loyaltyCard;
	}

	public void setLoyaltyCard(Card loyaltyCard) {
		this.loyaltyCard = loyaltyCard;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public Date getDateTicket() {
		return dateTicket;
	}

	public void setDateTicket(Date date) {
		this.dateTicket = date;
	}

	private List<TicketLine> ticketLines = new ArrayList<TicketLine>();

	public long AddLine(Product product, Float price, long quantity) {
		TicketLine newLine = new TicketLine(this, product, quantity, price);
		ticketLines.add(newLine);
		this.amount = this.amount + newLine.getLineTotal();
		return newLine.getLineNumber();
	}

	public void delLine(int lineNumber) {
		if (lineNumber <= this.ticketLines.size()) {
			TicketLine lineToDel = this.ticketLines.get(lineNumber);
			lineToDel.setValid(false);
			this.amount = this.amount-lineToDel.getLineTotal();
		}
	}

	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public List<TicketLine> getTicketLines() {
		return ticketLines;
	}

	public void setTicketLines(List<TicketLine> ticketLines) {
		this.ticketLines = ticketLines;
	}
	
}
