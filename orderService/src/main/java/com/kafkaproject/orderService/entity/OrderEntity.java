package com.kafkaproject.orderService.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class OrderEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName;
    private int quantity;
    private double price;
    private String customerEmail;
	public OrderEntity(String customerEmail) {
		super();
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
	public OrderEntity(Long id, String productName, int quantity, double price) {
		super();
		this.id = id;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", productName=" + productName + ", quantity=" + quantity + ", price=" + price + "]";
	}
	public OrderEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
    
    
}

