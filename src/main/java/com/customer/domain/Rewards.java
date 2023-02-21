package com.customer.domain;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rewards implements Serializable  {

	private static final long serialVersionUID = 1L;

	private String name;

	private String email;

	private String phno;

	private Double rewards;

	@JsonInclude(Include.NON_NULL)
	public Date orderDate;
	
	public String month;
	
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

	public Double getRewards() {
		return rewards;
	}

	public void setRewards(Double rewards) {
		this.rewards = rewards;
	}
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String i) {
		this.month = i;
	}

}
