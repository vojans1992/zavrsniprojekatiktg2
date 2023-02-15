package com.iktpreobuka.zavrsni.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class AdminEntity extends UserEntity {

	public AdminEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AdminEntity(Integer id, @NotNull String email, @NotNull String password, @NotNull String name,
			@NotNull String lastName, @NotNull String username, @NotNull RoleEntity role) {
		super(id, email, password, name, lastName, username, role);
		// TODO Auto-generated constructor stub
	}

	

	
}
