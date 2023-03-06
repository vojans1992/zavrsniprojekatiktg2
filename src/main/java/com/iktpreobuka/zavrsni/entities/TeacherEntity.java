package com.iktpreobuka.zavrsni.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({ "handler", "hibernateLazyInitializer" })
public class TeacherEntity extends UserEntity {

	@JsonIgnore
	@OneToOne(mappedBy = "homeroomTeacher", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private DepartmentEntity department;

	@JsonIgnore
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<GradeEntity> grades = new ArrayList<GradeEntity>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<TeacherSubjectDepartmentEntity> teacherSubjectDepartments = new ArrayList<TeacherSubjectDepartmentEntity>();


	public TeacherEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherEntity(Integer id, @NotNull String email, @NotNull String password, @NotNull String name,
			@NotNull String lastName, @NotNull RoleEntity role) {
		super(id, email, password, name, lastName, role);
		// TODO Auto-generated constructor stub
	}

	public TeacherEntity(DepartmentEntity department, List<GradeEntity> grades) {
		super();
		this.department = department;
		this.grades = grades;
	}

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}

	public List<GradeEntity> getGrades() {
		return grades;
	}

	public void setGrades(List<GradeEntity> grades) {
		this.grades = grades;
	}


	public List<TeacherSubjectDepartmentEntity> getTeacherSubjectDepartments() {
		return teacherSubjectDepartments;
	}

	public void setTeacherSubjectDepartments(List<TeacherSubjectDepartmentEntity> teacherSubjectDepartments) {
		this.teacherSubjectDepartments = teacherSubjectDepartments;
	}

	
	
}
