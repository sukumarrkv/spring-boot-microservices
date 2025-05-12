package com.sukumar.bookstore.orders.domain;

import java.time.LocalDateTime;
import java.util.Set;

import com.sukumar.bookstore.orders.domain.models.Address;
import com.sukumar.bookstore.orders.domain.models.Customer;
import com.sukumar.bookstore.orders.domain.models.OrderStatus;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String orderNumber;
	
	@Column(name = "username", nullable = false)
	private String userName;
	
	@Embedded
	@AttributeOverrides(
			value = {
					@AttributeOverride(name = "name", column = @Column(name = "customer_name")),
					@AttributeOverride(name = "emailAddress", column = @Column(name = "customer_email")),
					@AttributeOverride(name = "phoneNumber", column = @Column(name = "customer_phone"))
			})
	private Customer customer;
	
	
	@Embedded
	@AttributeOverrides(
			value = {
					@AttributeOverride(name = "addressLine1", column = @Column(name = "delivery_address_line1")),
					@AttributeOverride(name = "addressLine2", column = @Column(name = "delivery_address_line2")),
					@AttributeOverride(name = "city", column = @Column(name = "delivery_address_city")),
					@AttributeOverride(name = "state", column = @Column(name = "delivery_address_state")),
					@AttributeOverride(name = "zipCode", column = @Column(name = "delivery_address_zip_code")),
					@AttributeOverride(name = "country", column = @Column(name = "delivery_address_country"))
			})
	private Address deliveryAddress;
	//Advantage of using this is we can reuse the address class to define billing address in future if required instead of writing all the individual attributes in this class
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	private String comments;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();
	
	private LocalDateTime updatedAt;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "order", fetch = FetchType.EAGER)
	private Set<OrderItemEntity> items;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(Address deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Set<OrderItemEntity> getItems() {
		return items;
	}

	public void setItems(Set<OrderItemEntity> items) {
		this.items = items;
	}
	
}
