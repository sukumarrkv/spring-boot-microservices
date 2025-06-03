package com.sukumar.bookstore.notifications.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.sukumar.bookstore.notifications.config.ApplicationProperties;
import com.sukumar.bookstore.notifications.domain.models.OrderCancelledEvent;
import com.sukumar.bookstore.notifications.domain.models.OrderCreatedEvent;
import com.sukumar.bookstore.notifications.domain.models.OrderDeliveredEvent;
import com.sukumar.bookstore.notifications.domain.models.OrderErrorEvent;

import jakarta.mail.internet.MimeMessage;

@Service
public class NotificationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

	private JavaMailSender javaMailSender;
	private ApplicationProperties applicationProperties;
	
	public NotificationService(JavaMailSender javaMailSender, ApplicationProperties applicationProperties) {
		this.javaMailSender = javaMailSender;
		this.applicationProperties = applicationProperties;
	}

	public void sendOrderCreatedEmailNotification(OrderCreatedEvent event) {
		String emailContent = """
                ===================================================
                Order Created Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been created successfully.

                Thanks,
                BookStore Team
                ===================================================
                
				""".formatted(event.customer().name(), event.orderNumber());
		
		LOGGER.info("Sending order created email notification for order number : "+ event.orderNumber());
		sendEmail(event.customer().emailAddress(), "Order Created Notification", emailContent);
	}
	
	public void sendOrderDeliveredEmailNotification(OrderDeliveredEvent event) {
		String emailContent = """
                ===================================================
                Order Delivered Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been delivered successfully.

                Thanks,
                BookStore Team
                ===================================================
				""".formatted(event.customer().name(), event.orderNumber());
		
		LOGGER.info("Sending order delivered email notification for order number : "+ event.orderNumber());
		sendEmail(event.customer().emailAddress(), "Order Delivered Notification", emailContent);
	}
	
	public void sendOrderCancelledEmailNotification(OrderCancelledEvent event) {
		String emailContent = """
                ===================================================
                Order Cancelled Notification
                ----------------------------------------------------
                Dear %s,
                Your order with orderNumber: %s has been cancelled.
                Reason: %s

                Thanks,
                BookStore Team
                ===================================================
				""".formatted(event.customer().name(), event.orderNumber(), event.reason());
		
		LOGGER.info("Sending order cancelled email notification for order number : "+ event.orderNumber());
		sendEmail(event.customer().emailAddress(), "Order Cancelled Notification", emailContent);
	}
	
	public void sendOrderErrorEmailNotification(OrderErrorEvent event) {
		String emailContent = """
                ===================================================
                Order Processing Failure Notification
                ----------------------------------------------------
                Hi %s,
                The order processing failed for orderNumber: %s.
                Reason: %s

                Thanks,
                BookStore Team
                ===================================================
				""".formatted(event.customer().name(), event.orderNumber(), event.reason());
		
		LOGGER.info("Sending order error email notification for order number : "+ event.orderNumber());
		sendEmail(applicationProperties.supportEmail(), "Order Error Notification", emailContent);
	}
	
	private void sendEmail(String recipient, String emailSubject, String emailContent) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setFrom(applicationProperties.supportEmail());
			helper.setTo(recipient);
			helper.setSubject(emailSubject);
			helper.setText(emailContent);
			javaMailSender.send(mimeMessage);
			LOGGER.info("Sent order created email notification to : "+ recipient);
		}catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
