package com.customer.data;

import java.util.List;

public class Customer {

	private String name;

	private String email;

	private String phno;

	private List<Orders> orders;
	
	public Customer(String name, String email, String phno, List<Orders> orders) {
		this.name = name;
		this.email = email;
		this.phno = phno;
		this.orders = orders;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhno() {
		return phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
	}

	public List<Orders> getOrders() {
		return orders;
	}

	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}

}
