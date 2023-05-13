package com.iktpreobuka.zavrsni.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.security.Views;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class UserEntity {

	@JsonView(Views.Private.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Integer id;
	
	@JsonView(Views.Private.class)
	@NotNull(message = "Email must be provided")
	@Pattern(regexp = "^((?!\\.)[\\w-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message="Dto email is not valid.")
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	
	@NotNull(message = "Password must be provided.")
	@JsonView(Views.Admin.class)
	@Column(name = "password", nullable = false)
	private String password;
	
	@JsonView(Views.Public.class)
	@NotNull(message = "First name must be provided.")
	@Size(min=2, max=30, message = "First name must be between {min} and {max} characters long.")
	@Column(name = "name", nullable = false)
	private String name;
	
	@JsonView(Views.Public.class)
	@NotNull(message = "Last name must be provided.")
	@Size(min=2, max=30, message = "Last name must be between {min} and {max} characters long.")
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	
	@JsonView(Views.Admin.class)
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "role", nullable = false)
	private RoleEntity role;
	
	public UserEntity() {
		super();
	}

	public UserEntity(Integer id, @NotNull String email, @NotNull String password, @NotNull String name,
			@NotNull String lastName, @NotNull RoleEntity role) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.lastName = lastName;
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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


	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}
	
}
