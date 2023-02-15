package com.iktpreobuka.zavrsni.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class ParentEntity extends UserEntity{

	@JsonIgnore
	@OneToMany(mappedBy = "parent", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<PupilEntity> children = new ArrayList<PupilEntity>();
	
	public ParentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ParentEntity(Integer id, @NotNull String email, @NotNull String password, @NotNull String name,
			@NotNull String lastName, @NotNull String username, @NotNull RoleEntity role) {
		super(id, email, password, name, lastName, username, role);
		// TODO Auto-generated constructor stub
	}

	public ParentEntity(List<PupilEntity> children) {
		super();
		this.children = children;
	}

	public List<PupilEntity> getChildren() {
		return children;
	}

	public void setChildren(List<PupilEntity> children) {
		this.children = children;
	}

	
}
