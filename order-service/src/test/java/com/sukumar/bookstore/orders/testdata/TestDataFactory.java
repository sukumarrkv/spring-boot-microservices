package com.sukumar.bookstore.orders.testdata;

import static org.instancio.Select.field;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.instancio.Instancio;

import com.sukumar.bookstore.orders.domain.models.Address;
import com.sukumar.bookstore.orders.domain.models.CreateOrderRequest;
import com.sukumar.bookstore.orders.domain.models.Customer;
import com.sukumar.bookstore.orders.domain.models.OrderItem;

public class TestDataFactory {
	
	private static final List<String> VALID_COUNTRIES = List.of("India", "USA", "UK");
    private static final Set<OrderItem> VALID_ORDER_ITEMS =
            Set.of(new OrderItem("P100", "Product 1", new BigDecimal("30.50"), 1));
    private static final Set<OrderItem> INVALID_ORDER_ITEMS =
            Set.of(new OrderItem("ABCD", "Product 1", new BigDecimal("30.50"), 1));

	public static CreateOrderRequest createValidOrderRequest() {
		return Instancio.of(CreateOrderRequest.class)
				.generate(field(Customer::emailAddress), gen -> gen.text().pattern("#a#a#a#a#a#a@mail.com"))
				.set(field(CreateOrderRequest::items), VALID_ORDER_ITEMS)
				.generate(field(Address::country), gen -> gen.oneOf(VALID_COUNTRIES))
				.create();
	}
	
	public static CreateOrderRequest createOrderRequestWithInvalidCustomer() {
		return Instancio.of(CreateOrderRequest.class)
				.generate(field(Customer::emailAddress), gen -> gen.text().pattern("#a#a#a#a#a#a@mail.com"))
				.set(field(Customer::emailAddress), "")
				.set(field(CreateOrderRequest::items), VALID_ORDER_ITEMS)
				.generate(field(Address::country), gen -> gen.oneOf(VALID_COUNTRIES))
				.create();
	}
	
	public static CreateOrderRequest createOrderRequestWithInvalidDeliveryAddress() {
		return Instancio.of(CreateOrderRequest.class)
				.generate(field(Customer::emailAddress), gen -> gen.text().pattern("#a#a#a#a#a#a@mail.com"))
				.set(field(CreateOrderRequest::items), VALID_ORDER_ITEMS)
				.set(field(Address::country), "")
				.create();
	}
	
	public static CreateOrderRequest createOrderRequestWithNoItems() {
		return Instancio.of(CreateOrderRequest.class)
				.generate(field(Customer::emailAddress), gen -> gen.text().pattern("#a#a#a#a#a#a@mail.com"))
				.generate(field(Address::country), gen -> gen.oneOf(VALID_COUNTRIES))
				.set(field(CreateOrderRequest::items), Set.of())
				.create();
	}
}
