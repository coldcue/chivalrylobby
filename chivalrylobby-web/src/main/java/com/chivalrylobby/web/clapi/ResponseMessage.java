package com.chivalrylobby.web.clapi;

public class ResponseMessage {
	private boolean success;
	private String message;
	private long id;

	public ResponseMessage(boolean success, String message) {
		this.success = success;
		this.message = message;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
