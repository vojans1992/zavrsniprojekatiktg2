package com.iktpreobuka.zavrsni.entities.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PupilDto {

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
	@NotNull(message = "Username must be provided.")
	@Size(min=5, max=15, message = "Username must be between {min} and {max} characters long.")
	private String username;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull(message = "Password must be provided.")
	@Size(min=5, max=10, message = "Password must be between {min} and {max} characters long.")
	private String password;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull(message = "Password must be provided.")
	@Size(min=5, max=10, message = "Confirmed Password must be between {min} and {max} characters long.")
	private String confirmedPassword;
	@NotNull(message = "PupilDto must have department id!")
	private Integer departmentId;
	@NotNull(message = "PupilDto must have parent id!")
	private Integer parentId;
	public PupilDto() {
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	
	
}
