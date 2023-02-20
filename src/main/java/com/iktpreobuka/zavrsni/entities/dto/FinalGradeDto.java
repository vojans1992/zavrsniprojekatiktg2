package com.iktpreobuka.zavrsni.entities.dto;

import javax.validation.constraints.NotNull;

import com.iktpreobuka.zavrsni.entities.Semester;

public class FinalGradeDto {

	@NotNull(message = "Final grade must be connected to a semester.")
	private Semester semester;
	@NotNull(message = "Final grade must be connected to a pupil.")
	private Integer pupilId;
	@NotNull(message = "Final grade must be connected to a subject.")
	private Integer subjectId;
	
	public FinalGradeDto() {
		super();
		// TODO Auto-generated constructor stub
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

	public Integer getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Integer subjectId) {
		this.subjectId = subjectId;
	}
	
	
}
