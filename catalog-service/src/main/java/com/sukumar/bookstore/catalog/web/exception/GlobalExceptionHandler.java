package com.sukumar.bookstore.catalog.web.exception;

import java.net.URI;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	private static final URI ISE_FOUND_TYPE = URI.create("https://api.bookstore.com/errors/server-error");
	private static final URI NOT_FOUND_TYPE = URI.create("https://api.bookstore.com/errors/not-found");
	private static final String SERVCIE_NAME = "catalog-service";
	
	@ExceptionHandler(Exception.class)
	public ProblemDetail handleUnhandledException(Exception e) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		problemDetail.setTitle("Internal Server Error");
		problemDetail.setType(ISE_FOUND_TYPE);
		problemDetail.setProperty("service", SERVCIE_NAME);
		problemDetail.setProperty("error_category", "Generic");
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ProblemDetail handleProductNotFoundException(Exception e) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
		problemDetail.setTitle("Product Not Found");
		problemDetail.setType(NOT_FOUND_TYPE);
		problemDetail.setProperty("service", SERVCIE_NAME);
		problemDetail.setProperty("error_category", "Generic");
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}
}
