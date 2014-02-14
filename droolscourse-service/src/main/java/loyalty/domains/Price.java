package loyalty.domains;

public class Price {

	private Float price;
	private Currency currency;

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Price(Float price, Currency currency) {
		super();
		this.price = price;
		this.currency = currency;
	}

	public Price() {
		super();
		// TODO Auto-generated constructor stub
	}

}
