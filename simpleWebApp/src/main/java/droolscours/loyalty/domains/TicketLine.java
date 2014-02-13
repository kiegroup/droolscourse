package droolscours.loyalty.domains;

public class TicketLine {
	private static int numberLines = 0;
	private String ticketID;
	private Ticket ticket;
	private int lineNumber;
	private String productID;
	private Product product;
	private long quantity;
	private Float price = null;
	private Float lineTotal;;
	private Ligneop op;
	public Ligneop getOp() {
		return op;
	}

	public String getProductID() {
		return productID;
	}

	public void setProductID(String productID) {
		this.productID = productID;
	}

	public String getTicketID() {
		return this.ticketID;
	}

	public void setTicketID(String ticketID) {
		this.ticketID = ticketID;
	}

	public void setOp(Ligneop op) {
		this.op = op;
	}
	private boolean valid;

	public int getLineNumber() {
		return lineNumber;
	}

	public Float getLineTotal() {
		return lineTotal;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getQuantity() {
		return quantity;
	}

	public TicketLine(Ticket ticket, Product product, long quantity, Float price) {
		super();
		numberLines = numberLines + 1;
		this.lineNumber = numberLines;
		this.ticket = ticket;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.lineTotal = price * quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public TicketLine() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static int getNumberLines() {
		return numberLines;
	}

	public static void setNumberLines(int numberLines) {
		TicketLine.numberLines = numberLines;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public void setLineTotal(Float lineTotal) {
		this.lineTotal = lineTotal;
	}
	public void AddProductToTicketLine(Product p){
		this.product = p;
	}
}
