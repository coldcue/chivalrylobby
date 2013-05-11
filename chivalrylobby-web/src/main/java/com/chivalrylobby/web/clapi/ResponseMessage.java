package com.chivalrylobby.web.clapi;

public class ResponseMessage {
	private boolean success;
	private String message;
	private Long id = (long) 0;

	public ResponseMessage(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public ResponseMessage(boolean success, String message, long id) {
		this.success = success;
		this.message = message;
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
