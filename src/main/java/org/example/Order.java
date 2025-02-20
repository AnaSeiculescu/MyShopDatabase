package org.example;

import java.util.Date;

public class Order {
	private int id;
	private Date date;
	private String address;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", date=" + date +
				", address='" + address + '\'' +
				'}';
	}
}
