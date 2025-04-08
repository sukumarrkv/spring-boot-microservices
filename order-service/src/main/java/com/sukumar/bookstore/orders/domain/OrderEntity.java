package com.sukumar.bookstore.orders.domain;

import com.sukumar.bookstore.orders.domain.models.Customer;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
