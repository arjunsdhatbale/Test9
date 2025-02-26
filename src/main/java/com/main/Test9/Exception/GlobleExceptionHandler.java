package com.main.Test9.Exception;

 import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

 
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

//@ControllerAdvice
@RestControllerAdvice
public class GlobleExceptionHandler {
	
	private final static Logger logger = LoggerFactory.getLogger(GlobleExceptionHandler.class);
	
	@ExceptionHandler(ProductFetchException.class)
	public ResponseEntity<String> productFetechException(ProductFetchException ex){
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ex.getMessage());
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<String> unauthorizedException(UnauthorizedException ex){
		  logger.error("Unauthorized access: {}", ex.getMessage());
		  return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
		 
	}
	
	@ExceptionHandler(DuplicateProductException.class)
	public ResponseEntity<Map<String, Object>> duplicateProductException(DuplicateProductException ex){
		
		
		Map<String, Object> response = new HashMap<>();
		response.put("error", "Duplicate Product");
		response.put("message", ex.getMessage());
		response.put("timestamp", Instant.now());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> productNotFoundException(ProductNotFoundException ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
		return ResponseEntity.badRequest().body(errors);

	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> userNotFoundException(UserNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(ResourceConflictException.class)
	public ResponseEntity<String> resourceConflictException(ResourceConflictException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<String> dataIntegrityVoilationException(DataIntegrityViolationException ex) {
		return new ResponseEntity<String>(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An Unexpected error Ocdured." + e.getMessage());
	}

}
