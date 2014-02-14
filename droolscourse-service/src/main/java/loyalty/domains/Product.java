package loyalty.domains;

public class Product {

	private String Id;
	private String Name;
	private Price price;

	public Price getPrice() {
		return price;
	}

	public void setPrice(Price price) {
		this.price = price;
	}

	private Provider provider;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public Product(String id, String name, Provider provider) {
		super();
		Id = id;
		Name = name;
		this.provider = provider;
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

}
