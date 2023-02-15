package com.iktpreobuka.zavrsni.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class PupilEntity extends UserEntity{
	@NotNull(message = "Pupil must belong to a school year.")
	@Column(name = "year", nullable = false)
	private Year year;
	@NotNull(message = "Pupil must belong to a department.")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn (name= "department", nullable = false)
	private DepartmentEntity department;
	@NotNull(message = "Pupil must have a parent.")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn (name= "parent", nullable = false)
	private ParentEntity parent;
	@JsonIgnore
	@OneToMany(mappedBy = "pupil", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<GradeEntity> grades = new ArrayList<GradeEntity>();
	@JsonIgnore
	@OneToMany(mappedBy = "pupil", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<FinalGradeEntity> finalGrades = new ArrayList<FinalGradeEntity>();
	
	public PupilEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PupilEntity(Integer id, @NotNull String email, @NotNull String password, @NotNull String name,
			@NotNull String lastName, @NotNull String username, @NotNull RoleEntity role) {
		super(id, email, password, name, lastName, username, role);
		// TODO Auto-generated constructor stub
	}

	public PupilEntity(@NotNull Year year, @NotNull DepartmentEntity department, @NotNull ParentEntity parent,
			List<GradeEntity> grades, List<FinalGradeEntity> finalGrades) {
		super();
		this.year = year;
		this.department = department;
		this.parent = parent;
		this.grades = grades;
		this.finalGrades = finalGrades;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}

	public ParentEntity getParent() {
		return parent;
	}

	public void setParent(ParentEntity parent) {
		this.parent = parent;
	}

	public List<GradeEntity> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeEntity> grades) {
		this.grades = grades;
	}

	public List<FinalGradeEntity> getFinalGrades() {
		return finalGrades;
	}

	public void setFinalGrades(List<FinalGradeEntity> finalGrades) {
		this.finalGrades = finalGrades;
	}
	
	
	
	
	
}