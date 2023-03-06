package com.iktpreobuka.zavrsni.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.security.Views;

@Entity
@Table(name = "department")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class DepartmentEntity {

	@JsonView(Views.Admin.class)
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "department_id")
	private Integer id;
	@JsonView(Views.Public.class)
	@Column(name = "name", nullable = false)
	@NotNull(message = "Department must have a name.")
	private String name;
	@JsonView(Views.Public.class)
	@Column(name = "year", nullable = false)
	@NotNull(message = "Department must be in a school year.")
	private Year year;
	@JsonView(Views.Public.class)
	@NotNull(message = "Department must have a homeroom teacher.")
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "homeroomTeacher", unique = true, nullable = false)
	private TeacherEntity homeroomTeacher;
	@JsonIgnore
	@OneToMany(mappedBy = "department", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<PupilEntity> pupils = new ArrayList<PupilEntity>();
	@JsonIgnore
	@OneToMany(mappedBy = "department", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<TeacherSubjectDepartmentEntity> teacherSubjectDepartments = new ArrayList<TeacherSubjectDepartmentEntity>();
	
	public DepartmentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DepartmentEntity(Integer id, Year year, TeacherEntity teacherEntity) {
		super();
		this.id = id;
		this.year = year;
		this.homeroomTeacher = teacherEntity;
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

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public TeacherEntity getHomeroomTeacher() {
		return homeroomTeacher;
	}

	public void setHomeroomTeacher(TeacherEntity homeroomTeacher) {
		this.homeroomTeacher = homeroomTeacher;
	}

	public List<PupilEntity> getPupils() {
		return pupils;
	}

	public void setPupils(List<PupilEntity> pupils) {
		this.pupils = pupils;
	}

	public List<TeacherSubjectDepartmentEntity> getTeacherSubjectDepartments() {
		return teacherSubjectDepartments;
	}

	public void setTeacherSubjectDepartments(List<TeacherSubjectDepartmentEntity> teacherSubjectDepartments) {
		this.teacherSubjectDepartments = teacherSubjectDepartments;
	}
	
	
}
