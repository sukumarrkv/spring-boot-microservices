package com.sukumar.bookstore.orders.domain.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//@Embeddable - This is not required from hibernate 6.2 and above
public record Customer(
		@NotBlank(message = "Customer name is required")
		String name,
		@NotBlank(message = "Customer email address is required")
		@Email
		String emailAddress,
		@NotBlank(message = "Customer phone number is required")
		String phoneNumber
		) {

}
