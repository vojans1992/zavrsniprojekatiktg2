package com.iktpreobuka.zavrsni.entities;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.security.Views;

@Entity
@Table(name = "subject")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class SubjectEntity {

	@JsonView(Views.Admin.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "subject_id")
	private Integer id;
	@JsonView(Views.Public.class)
	@NotNull(message = "Subject must have a name.")
	@Column(name = "name", nullable = false)
	private String name;
	@JsonView(Views.Private.class)
	@NotNull(message = "Subject must have weekly classload.")
	@Column(name = "class_load", nullable = false)
	private Integer classLoad;
	@JsonView(Views.Private.class)
	@NotNull(message = "Subject must have a school year it belongs to.")
	@Column(name = "year", nullable = false)
	private Year year;
	@JsonIgnore
	@OneToMany(mappedBy = "subject", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<GradeEntity> grades = new ArrayList<GradeEntity>();
	@JsonIgnore
	@OneToMany(mappedBy = "subject", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<FinalGradeEntity> finalGrades = new ArrayList<FinalGradeEntity>();
	@JsonIgnore
	@OneToMany(mappedBy = "subject", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<TeacherSubjectDepartmentEntity> teacherSubjectDepartments = new ArrayList<TeacherSubjectDepartmentEntity>();
	
	
	public SubjectEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubjectEntity(Integer id, @NotNull String name, @NotNull Integer classLoad, @NotNull Year year,
			List<GradeEntity> grades, List<FinalGradeEntity> finalGrades) {
		super();
		this.id = id;
		this.name = name;
		this.classLoad = classLoad;
		this.year = year;
		this.grades = grades;
		this.finalGrades = finalGrades;
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

	public Integer getClassLoad() {
		return classLoad;
	}

	public void setClassLoad(Integer classLoad) {
		this.classLoad = classLoad;
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
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


	public List<TeacherSubjectDepartmentEntity> getTeacherSubjectDepartments() {
		return teacherSubjectDepartments;
	}

	public void setTeacherSubjectDepartments(List<TeacherSubjectDepartmentEntity> teacherSubjectDepartments) {
		this.teacherSubjectDepartments = teacherSubjectDepartments;
	}
	
	
}
