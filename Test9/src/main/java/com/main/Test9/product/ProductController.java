package com.main.Test9.product;

 
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;

import com.fasterxml.jackson.annotation.JsonView;
import com.main.Test9.JsonViewResponse;
import com.main.Test9.product.ProductMaster.BasicView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@Tag(name = "ProductController",description = "API for Product operations.")
public class ProductController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductMaster.class);
	
	@Autowired
	private ProductService productService;
	
	@PostMapping("/")
	@Operation(summary = "Save Product", description = "Save the different Products.")
	public ResponseEntity<ProductMaster> saveProduct(@Valid @RequestBody(required = true) ProductMaster productMaster){
		try {
			logger.info("Request receive to save product");
			ProductMaster product = this.productService.saveProducts(productMaster);
			return new ResponseEntity<ProductMaster>(product,HttpStatus.CREATED);	
		}catch(IllegalArgumentException e) {
			logger.error("error occured while saving the product");
			return new ResponseEntity<ProductMaster>(HttpStatus.BAD_REQUEST);
		} 
	}
	
	@GetMapping(value = "/",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Get All Product", description =   "Fetch all Products List.")
	@JsonView(ProductMaster.BasicView.class)
	public ResponseEntity<?> getAllBasicProducts(
			@RequestHeader(name = "User-Role", required = false,defaultValue = "basic") String role
			) {
		logger.info("Request received to fetech all Products..");	
		List<ProductMaster> products = this.productService.findAll();
		
		if("Admin".equalsIgnoreCase(role)) {
			logger.info("Returning AdminView data");
			return ResponseEntity.ok().body(new JsonViewResponse<>(ProductMaster.AdminView.class,products));
//			return new ResponseEntity<>(products,HttpStatus.OK);
		}else {
			logger.info("Returning basicView Data");
			return ResponseEntity.ok().body(new JsonViewResponse<>(ProductMaster.BasicView.class,products));
//			return new ResponseEntity<>(products,HttpStatus.OK);	
		}
	}
	
	 
	
	@GetMapping("/searchProduct")
	public ResponseEntity<ProductMaster> findByProductIdOrProductName(
			@RequestParam(required = false) Long productId,
			@RequestParam(required = false) String productName
			){
		Optional<ProductMaster> product = this.productService.findByIdOrProductName(productId,productName);
		if(product.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(product.get());	
		}else {
			logger.warn("No product found by product id : {}  or productName : {}", productId,productName);
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}
	
//	@PatchMapping("/")
//	public ResponseEntity<T>
	
}




