package loyalty.domains;

public class Provider {
	private String name;
	private String country;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Provider(String name, String country) {
		super();
		this.name = name;
		this.country = country;
	}

	public Provider() {
		super();
		// TODO Auto-generated constructor stub
	}

}
