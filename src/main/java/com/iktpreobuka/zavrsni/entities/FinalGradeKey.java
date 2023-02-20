package com.iktpreobuka.zavrsni.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FinalGradeKey implements Serializable{

	@Column(name = "pupil_id")
	private Integer pupilId;
	
	@Column(name = "subject_id")
	private Integer subjectId;

	public FinalGradeKey() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public FinalGradeKey(Integer pupilId, Integer subjectId) {
		super();
		this.pupilId = pupilId;
		this.subjectId = subjectId;
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
