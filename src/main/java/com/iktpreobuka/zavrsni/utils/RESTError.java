package com.iktpreobuka.zavrsni.utils;

public class RESTError {

	private int code;
	private String message;

	public RESTError(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
