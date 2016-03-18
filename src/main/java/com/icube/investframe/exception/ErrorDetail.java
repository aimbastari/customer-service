package com.icube.investframe.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorDetail {
	
	private String title;
	private int status;
	private String detail;
	private long timestamp;
	private String developerMessage;
	private Map<String, List<ValidationError>> errors = new HashMap<String, List<ValidationError>>();
	
	public ErrorDetail() {}
	
	
	public ErrorDetail(String title, int status, String detail, long timestamp, String developerMessage, Map<String, List<ValidationError>> errors) {
		super();
		this.title = title;
		this.status = status;
		this.detail = detail;
		this.timestamp = timestamp;
		this.developerMessage = developerMessage;
		this.errors = errors;
		
	}

	public Map<String, List<ValidationError>> getErrors() {
		return errors;
	}


	public void setErrors(Map<String, List<ValidationError>> errors) {
		this.errors = errors;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getDeveloperMessage() {
		return developerMessage;
	}

	public void setDeveloperMessage(String developerMessage) {
		this.developerMessage = developerMessage;
	}
	
	
	
}
