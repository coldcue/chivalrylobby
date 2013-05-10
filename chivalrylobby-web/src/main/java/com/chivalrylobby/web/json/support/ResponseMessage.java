package com.chivalrylobby.web.json.support;

public class ResponseMessage {
	private boolean success;
	private String message;

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

}
