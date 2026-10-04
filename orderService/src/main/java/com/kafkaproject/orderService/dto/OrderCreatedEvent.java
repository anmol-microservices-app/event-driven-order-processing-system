package com.kafkaproject.orderService.dto;

public class OrderCreatedEvent {

	private Long id;
	private String productName;
	private int quantity;
	private double price;
	private String customerEmail;

	public OrderCreatedEvent() {
	}

	public OrderCreatedEvent(Long id, String productName, int quantity, double price, String customerEmail) {
		this.id = id;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.customerEmail = customerEmail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
}