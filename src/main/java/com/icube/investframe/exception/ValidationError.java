package com.icube.investframe.exception;

public class ValidationError {

    private String code;
    private String message;
    
	public ValidationError(String code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public ValidationError() {
		super();
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
 
    
}    

 