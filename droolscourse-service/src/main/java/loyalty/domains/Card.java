package loyalty.domains;

public class Card {
	private String number;
	private String name;

	private Customer customer;

	public void setNumber(String number) {
		this.number = number;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setCartType(String cartType) {
		this.cartType = cartType;
	}

	private String cartType;

	public Card(String number, String cartName, String cartType,
			String customerID, String name, String surname, String maritalName,
			Gender gender) {
		this.number = number;
		this.name = cartName;
		this.cartType = cartType;
		this.customer = new Customer(customerID, name, surname, maritalName,
				gender, null);

	}

	public String getName() {
		return name;
	}

	public String getCartType() {
		return cartType;
	}

	public String getNumber() {
		return number;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Card() {
		super();
		// TODO Auto-generated constructor stub
	}

}
