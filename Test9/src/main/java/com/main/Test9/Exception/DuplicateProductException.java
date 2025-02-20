package com.main.Test9.Exception;

 
public class DuplicateProductException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateProductException(String message) {
		super(message);
	}
}
