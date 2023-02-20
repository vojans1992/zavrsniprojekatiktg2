package com.iktpreobuka.zavrsni.entities.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.iktpreobuka.zavrsni.entities.Semester;

public class GradeDto {

	@Min(1)
	@Max(5)
	@NotNull
	private Integer value;
	
	@NotNull(message = "Grade must be connected to a semester.")
	private Semester semester;
	
	@NotNull(message = "Grade must be connected to a pupil.")
	private Integer pupilId;
	
	@NotNull(message = "Grade must be connected to a teacher.")
	private Integer teacherId;
	
	@NotNull(message = "Grade must be connected to a subject.")
	private Integer subjectId;

	public GradeDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public Integer getPupilId() {
		return pupilId;
	}

	public void setPupilId(Integer pupilId) {
		this.pupilId = pupilId;
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
	
	
}
