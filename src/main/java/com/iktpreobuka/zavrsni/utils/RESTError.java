package com.iktpreobuka.zavrsni.utils;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.security.Views;

public class RESTError {

	@JsonView(Views.Public.class)
	private int code;
	@JsonView(Views.Public.class)
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
