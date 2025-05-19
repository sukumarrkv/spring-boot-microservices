package com.sukumar.bookstore.orders.jobs;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sukumar.bookstore.orders.domain.OrderEventService;

import net.javacrumbs.shedlock.core.LockAssert;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Component
public class OrderEventsPublishingJob {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventsPublishingJob.class);

	private OrderEventService orderEventService;

	public OrderEventsPublishingJob(OrderEventService orderEventService) {
		this.orderEventService = orderEventService;
	}
	
	@Scheduled(cron = "${orders.publish-order-events-job-cron}")
	@SchedulerLock(name = "publishOrderEvents")
	public void publishOrderEvents() {
		LockAssert.assertLocked();
		LOGGER.info("Publishing Order Events at :" + Instant.now());
		orderEventService.publishOrderEvents();
	}
}
