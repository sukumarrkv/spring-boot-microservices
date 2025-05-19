package com.sukumar.bookstore.orders.jobs;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sukumar.bookstore.orders.domain.OrderService;

import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Component
public class OrderProcessingJob {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderProcessingJob.class);

	private OrderService orderService;

	public OrderProcessingJob(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@Scheduled(cron = "${orders.new-order-processing-job-cron}")
	@SchedulerLock(name = "processNewOrders")
	public void processNewOrders() {
		LockAssert.assertLocked();
		LOGGER.info("Processing order at :" + Instant.now());
		orderService.processNewOrders();;
	}
}
