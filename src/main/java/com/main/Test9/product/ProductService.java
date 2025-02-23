package com.main.Test9.product;

import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.main.Test9.Exception.DuplicateProductException;
import com.main.Test9.Exception.ProductFetchException;
import com.main.Test9.Exception.ProductNotFoundException;
import com.main.Test9.Exception.DuplicateProductException;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	
	@Autowired
	private ProductRepository  productRepository;

	@Transactional
	public ProductMaster saveProducts(ProductMaster productMaster) {
		try {
			if(productMaster.getProductName() == null || productMaster.getProductName().isEmpty()) {
				throw new IllegalArgumentException("Product name can not be null or empty");
			}
			Optional<ProductMaster> optionalProduct = this.productRepository.findByProductName(productMaster.getProductName());
			if(optionalProduct.isPresent()) {
				logger.error("Dublice Name found : " + productMaster.getProductName());
				throw new DuplicateProductException("Product with name : " + productMaster.getProductName() + " already exist.");
			}
			
			return this.productRepository.save(productMaster);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException("Database constraints voilation : " + e.getMessage());
		}catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid product details : " + e.getMessage());
		}catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
	}

	public List<ProductMaster> findAll() {
		try {
//			List<ProductMaster> list = this.productRepository.findAll();
//			if(list.isEmpty()) {
//				logger.warn("No products found in database");
//			}
			return this.productRepository.findAll();
		}catch (ProductFetchException e) {
			 logger.error("Error while fetching all products: {}", e.getMessage(), e);
	            throw new ProductFetchException("Failed to fetch products due to an internal error.");
		}
	}

	public Optional<ProductMaster> findByIdOrProductName(Long productId, String productName) {
		Optional<ProductMaster> product = this.productRepository.findByProductIdOrProductName(productId, productName);
		if(product.isPresent()) {
			return  product;
		}else {
			throw new ProductNotFoundException("Product not found.");
		} 
	}
	
	
	
}
