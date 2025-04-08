package com.sukumar.bookstore.orders.domain.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

//@Embeddable - This is not required from hibernate 6 and above
public record Customer(
		@NotNull(message = "Customer name is required")
		String name,
		@NotNull(message = "Customer email address is required")
		@Email
		String emailAddress,
		@NotNull(message = "Customer phone number is required")
		String phoneNumber
		) {

}
