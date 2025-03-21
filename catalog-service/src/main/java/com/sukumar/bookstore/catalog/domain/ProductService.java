package com.sukumar.bookstore.catalog.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProductService {

	private ProductRepository productRepository;
	
	private ApplicationProperties applicationProperties;

	public ProductService(ProductRepository productRepository, ApplicationProperties applicationProperties) {
		this.productRepository = productRepository;
		this.applicationProperties = applicationProperties;
	}
	
	public PageResult<Product> getAllProducts(int pageNumber) {
		//It is always good practice to return the list sorted
		Sort sort = Sort.by("name").ascending();
		//In spring data jpa perspective index for page always starts with 0 and usually user asks send me
		//list of first page so we should perform below validation
		pageNumber = pageNumber <= 1 ? 0 : pageNumber - 1;
		Pageable pageable = PageRequest.of(pageNumber, applicationProperties.pageSize(), sort);
		Page<Product> pageResults = productRepository.findAll(pageable).map(ProductMapper::toProduct);
		return new PageResult<>(
				pageResults.getContent(), 
				pageResults.getTotalElements(),
				pageResults.getNumber() + 1, //While returning also we should increment by 1
				pageResults.getTotalPages(), 
				pageResults.isFirst(), 
				pageResults.isLast(), 
				pageResults.hasNext(), 
				pageResults.hasPrevious());
	}
}
