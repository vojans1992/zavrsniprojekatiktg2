package com.iktpreobuka.zavrsni.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.security.Views;

@Entity
@Table(name = "teacher_subject_department")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class TeacherSubjectDepartmentEntity {

	@JsonView(Views.Admin.class)
	@EmbeddedId
	@Column(name = "teacher_subject_department_id")
	private TeacherSubjectDepartmentKey id = new TeacherSubjectDepartmentKey();
	
	@JsonView(Views.Private.class)
	@NotNull(message = "TeacherSubjectDepartment must have a teacher.")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@MapsId("teacherId")
	@JoinColumn (name= "teacher_id", nullable = false)
	private TeacherEntity teacher;
	
	@JsonView(Views.Private.class)
	@NotNull(message = "TeacherSubjectDepartment must have a subject.")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@MapsId("subjectId")
	@JoinColumn (name= "subject_id", nullable = false)
	private SubjectEntity subject;
	
	@JsonView(Views.Private.class)
	@NotNull(message = "TeacherSubjectDepartment must have a department.")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@MapsId("departmentId")
	@JoinColumn (name= "department_id", nullable = false)
	private DepartmentEntity department;

	public TeacherSubjectDepartmentEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeacherSubjectDepartmentEntity(
			@NotNull(message = "TeacherSubjectDepartment must have a teacher.") TeacherEntity teacher,
			@NotNull(message = "TeacherSubjectDepartment must have a subject.") SubjectEntity subject,
			@NotNull(message = "TeacherSubjectDepartment must have a department.") DepartmentEntity department) {
		super();
		this.teacher = teacher;
		this.subject = subject;
		this.department = department;
	}



	public TeacherSubjectDepartmentKey getId() {
		return id;
	}

	public void setId(TeacherSubjectDepartmentKey id) {
		this.id = id;
	}

	public TeacherEntity getTeacher() {
		return teacher;
	}

	public void setTeacher(TeacherEntity teacher) {
		this.teacher = teacher;
	}

	public SubjectEntity getSubject() {
		return subject;
	}

	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}

	public DepartmentEntity getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentEntity department) {
		this.department = department;
	}
	
	
}
