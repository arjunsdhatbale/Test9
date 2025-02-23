package com.main.Test9.Exception;

public class UserNotFoundException extends  RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String message; 
	private Integer id; 
	
	
	public UserNotFoundException(Integer id) {
		super();
	}
	
	public UserNotFoundException(String message){
        super(message);
    }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "UserNotFoundException [message=" + message + ", id=" + id + "]";
	}
    
	
	
	
}
