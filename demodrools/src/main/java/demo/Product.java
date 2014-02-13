package demo;

import org.joda.time.DateTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Product {

	private String Id;
	private String Name;
	private Map<Date, Map<Currency, Price>> prices = new HashMap<Date, Map<Currency, Price>>();
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

	public void setPrice(Date startTime, Float price, Currency currency) {
		if (prices.containsKey(startTime) == false) {
			Price newprice = new Price(price, currency);
			Map<Currency, Price> priceMap = new HashMap<Currency, Price>();
			priceMap.put(currency, newprice);
			prices.put(startTime, priceMap);
		} else {
			Map<Currency, Price> priceMap = prices.get(startTime);
			if (priceMap.containsKey(currency) == false) {
				Price aNewPrice = new Price(price, currency);
				priceMap.put(currency, aNewPrice);
			} else {
				Price aExistingPrice = priceMap.get(currency);
				aExistingPrice.setPrice(price);
			}
		}
	}

	public void delPrice(DateTime startTime) {
		if (prices.containsKey(startTime) == true) {
			prices.remove(startTime);
		}
	}

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Map<Date, Map<Currency, Price>> getPrices() {
		return prices;
	}

	public void setPrices(Map<Date, Map<Currency, Price>> prices) {
		this.prices = prices;
	}

}
