package com.sukumar.bookstore.catalog.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sukumar.bookstore.catalog.domain.PageResult;
import com.sukumar.bookstore.catalog.domain.Product;
import com.sukumar.bookstore.catalog.domain.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private ProductService productService;

	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}
	
	@GetMapping
	public PageResult<Product> getAllProducts(@RequestParam(name = "page", defaultValue = "1") int pageNumber) {
		return productService.getAllProducts(pageNumber);
	}
}
