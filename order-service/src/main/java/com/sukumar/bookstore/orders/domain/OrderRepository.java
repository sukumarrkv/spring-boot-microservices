package com.sukumar.bookstore.orders.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sukumar.bookstore.orders.domain.models.OrderStatus;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{

	public List<OrderEntity> findByStatus(OrderStatus orderStatus);

	public Optional<OrderEntity> findByOrderNumber(String orderNumber);
}
