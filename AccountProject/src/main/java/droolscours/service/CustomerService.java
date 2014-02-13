package droolscours.service;

import java.util.ArrayList;
import java.util.List;

import droolscours.Customer;

public class CustomerService {

	public List<Customer> getListCustomer(){
		List<Customer> result = new ArrayList<Customer>();
		result.add(new Customer("Héron", "Nicolas", "Fr"));
		result.add(new Customer("Héron", "James", "GB"));
		result.add(new Customer("Héron", "Nicolas", "GB"));
		return result;
	}
	
}
