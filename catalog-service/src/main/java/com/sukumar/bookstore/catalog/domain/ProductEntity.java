package com.sukumar.bookstore.catalog.domain;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, unique = true)
	@NotNull(message = "Product Code is required")
	private String code;

	@Column(nullable = false)
	@NotNull(message = "Product name is required")
	private String name;

	private String description;

	private String imageUrl;

	@Column(nullable = false)
	@NotNull(message = "Product Price is required")
	@DecimalMin("0.1")
	private BigDecimal price;

	public ProductEntity() {
		super();
	}

	public ProductEntity(int id, @NotNull(message = "Product Code is required") String code,
			@NotNull(message = "Product name is required") String name, String description, String imageUrl,
			@NotNull(message = "Product Price is required") @DecimalMin("0.1") BigDecimal price) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.description = description;
		this.imageUrl = imageUrl;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
