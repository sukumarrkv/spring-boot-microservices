package com.sukumar.bookstore.orders.web.exception;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    private static final URI NOT_FOUND_TYPE = URI.create("https://api.bookstore.com/errors/not-found");
    private static final URI ISE_FOUND_TYPE = URI.create("https://api.bookstore.com/errors/server-error");
    private static final URI BAD_REQUEST_TYPE = URI.create("https://api.bookstore.com/errors/bad-request");
    private static final String SERVICE_NAME = "order-service";
	
	@ExceptionHandler(Exception.class)
	public ProblemDetail handleException(Exception e) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		problemDetail.setType(ISE_FOUND_TYPE);
		problemDetail.setTitle("Internal Service Error");
		problemDetail.setProperty("service", SERVICE_NAME);
		problemDetail.setProperty("error_category", "Generic");
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			errors.add(error.getDefaultMessage());
		});
		
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
		problemDetail.setTitle("Bad Request");
		problemDetail.setType(BAD_REQUEST_TYPE);
		problemDetail.setProperty("errors", errors);
		problemDetail.setProperty("service", SERVICE_NAME);
		problemDetail.setProperty("error_category", "Generic");
		problemDetail.setProperty("timestamp", Instant.now());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
	}
	
	@ExceptionHandler(InvalidOrderException.class)
	public ProblemDetail handleInvalidOrderException(InvalidOrderException ioe) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ioe.getMessage());
		problemDetail.setTitle("Invalid order request");
		problemDetail.setType(BAD_REQUEST_TYPE);
		problemDetail.setProperty("service", SERVICE_NAME);
		problemDetail.setProperty("error_category", "Generic");
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ProblemDetail handleOrderNotFoundException(OrderNotFoundException onfe) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, onfe.getMessage());
		problemDetail.setTitle("Order Not Found");
		problemDetail.setType(BAD_REQUEST_TYPE);
		problemDetail.setProperty("service", SERVICE_NAME);
		problemDetail.setProperty("error_category", "Generic");
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
		
	}
}
