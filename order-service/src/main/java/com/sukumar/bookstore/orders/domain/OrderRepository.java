package com.sukumar.bookstore.orders.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sukumar.bookstore.orders.domain.models.OrderStatus;
import com.sukumar.bookstore.orders.domain.models.OrderSummary;

public interface OrderRepository extends JpaRepository<OrderEntity, Long>{

	public List<OrderEntity> findByStatus(OrderStatus orderStatus);

	public Optional<OrderEntity> findByOrderNumber(String orderNumber);
	
	@Query("select new com.sukumar.bookstore.orders.domain.models.OrderSummary(o.orderNumber, o.status) from OrderEntity o where o.userName= :userName")
	public List<OrderSummary> findByUserName(String userName);
	
	public Optional<OrderEntity> findByOrderNumberAndUserName(String orderNumber, String orderStatus);
}
