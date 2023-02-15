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

	@OneToOne(mappedBy = "homeroomTeacher", fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	private DepartmentEntity department;

	@JsonIgnore
	@OneToMany(mappedBy = "teacher", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<GradeEntity> grades = new ArrayList<GradeEntity>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinTable(
			  name = "teacher_subject", 
			  joinColumns = {@JoinColumn(name = "teacher_id", nullable = false, updatable = false)}, 
			  inverseJoinColumns = { @JoinColumn(name = "subject_id", nullable = false, updatable = false)})
	private Set<SubjectEntity> subjects = new HashSet<SubjectEntity>();

	public TeacherEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherEntity(Integer id, @NotNull String email, @NotNull String password, @NotNull String name,
			@NotNull String lastName, @NotNull String username, @NotNull RoleEntity role) {
		super(id, email, password, name, lastName, username, role);
		// TODO Auto-generated constructor stub
	}

	public TeacherEntity(DepartmentEntity department, List<GradeEntity> grades, Set<SubjectEntity> subjects) {
		super();
		this.department = department;
		this.grades = grades;
		this.subjects = subjects;
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

	public Set<SubjectEntity> getSubjects() {
		return subjects;
	}

	public void setSubjects(Set<SubjectEntity> subjects) {
		this.subjects = subjects;
	}

}
