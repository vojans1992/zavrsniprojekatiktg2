package com.iktpreobuka.zavrsni.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.security.Views;

@Embeddable
public class TeacherSubjectDepartmentKey implements Serializable{

	@JsonView(Views.Admin.class)
	@Column(name = "teacher_id")
	private Integer teacherId;
	
	@JsonView(Views.Admin.class)
	@Column(name = "subject_id")
	private Integer subjectId;
	
	@JsonView(Views.Admin.class)
	@Column(name = "department_id")
	private Integer departmentId;

	public TeacherSubjectDepartmentKey() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public TeacherSubjectDepartmentKey(Integer teacherId, Integer subjectId, Integer departmentId) {
		super();
		this.teacherId = teacherId;
		this.subjectId = subjectId;
		this.departmentId = departmentId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
}
