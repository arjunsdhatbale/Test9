package com.main.Test9.product;

 
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;

import com.fasterxml.jackson.annotation.JsonView;
import com.main.Test9.JsonViewResponse;
import com.main.Test9.Exception.UnauthorizedException;
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
	public ResponseEntity<?> getAllProducts(
			@RequestHeader(name = "User-Role", required = false,defaultValue = "Admin") String role
			) {
		logger.info("Request received to fetech all Products..");
		
		List<ProductMaster> products = this.productService.findAll();
		
		if(products == null || products.isEmpty()) {
			logger.info("No product Found");
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No product Available.");
		}
		
		Class<?> viewClass = "Admin".equalsIgnoreCase(role) ? ProductMaster.AdminView.class : ProductMaster.BasicView.class;
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(products);
		
		mappingJacksonValue.setSerializationView(viewClass);
		System.out.println("output = " + mappingJacksonValue.getValue());
		return ResponseEntity.ok().body(mappingJacksonValue);
		
	}
	
	@GetMapping(value =  "/searchProduct", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Get Product by Product Id or Product Name", description = "Thid method find the produt by either product id or product Name.")
	public ResponseEntity<ProductMaster> findByProductIdOrProductName(
			@RequestHeader(name = "User-Role",required = false,defaultValue = "Basic") String role,
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
 
	@PutMapping(value = "/{productName}",produces = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
	@Operation(summary = "Update Product",description = "This method update the comple product")
	public ResponseEntity<ProductMaster> updateProduct(
			@RequestHeader(name = "User-Role",required = true,defaultValue = "Admin")String role,
			@PathVariable(required = true) String productName,
			@Valid @RequestBody(required = true) ProductMaster productMaster
			) throws Exception  {
		
		logger.info("Request received to update product.");
		
		if(!"Admin".equalsIgnoreCase(role)) {
			logger.warn("Un-authorized update attempt by user with role: {}",role);
			throw new UnauthorizedException("You do not have permission to update product");
		}
		ProductMaster product = productService.updateProduct(productName,productMaster);
		
		return ResponseEntity.status(HttpStatus.OK).body(product);
	}
}







		























