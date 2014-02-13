package droolscours.loyalty.domains;

import java.util.Date;

public class Customer {
	private String customerID;
	private String surName;
	private String name;
	private String maritalName;
	private Gender gender;
	private Date birthDate;

	public String getCustomerID() {
		return customerID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaritalName() {
		return maritalName;
	}

	public void setMaritalName(String maritalName) {
		this.maritalName = maritalName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Customer(String customerID, String surName, String name,
			String maritalName, Gender gender, Date birthDate) {
		super();
		this.customerID = customerID;
		this.surName = surName;
		this.name = name;
		this.maritalName = maritalName;
		this.gender = gender;
		this.birthDate = birthDate;
	}

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

}
