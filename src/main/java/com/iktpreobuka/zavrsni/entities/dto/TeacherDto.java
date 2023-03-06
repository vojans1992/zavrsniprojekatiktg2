package com.iktpreobuka.zavrsni.entities.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class TeacherDto {

	private Integer id;
	@NotNull(message = "First name must be provided")
	@Size(min = 2, max = 30, message =  "First name must be between 2 and 30 characters long.")
	private String name;
	@NotNull(message = "Last name must be provided")
	@Size(min = 2, max = 30, message =  "First name must be between 2 and 30 characters long.")
	private String lastName;
	@NotNull(message = "Email must be provided")
	@Pattern(regexp = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message="Dto email is not valid.")
	private String email;
	@NotNull(message = "Password must be provided.")
	@Size(min=5, max=10, message = "Password must be between {min} and {max} characters long.")
	private String password;
	@NotNull(message = "Password must be provided.")
	@Size(min=5, max=10, message = "Confirmed Password must be between {min} and {max} characters long.")
	private String confirmedPassword;
	

	public TeacherDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmedPassword() {
		return confirmedPassword;
	}

	public void setConfirmedPassword(String confirmedPassword) {
		this.confirmedPassword = confirmedPassword;
	}
	
	
}
