package com.iktpreobuka.zavrsni.entities.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.security.Views;

public class LoginUserDto {

	@JsonView(Views.Public.class)
	private String user;
	@JsonView(Views.Public.class)
	private String token;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
