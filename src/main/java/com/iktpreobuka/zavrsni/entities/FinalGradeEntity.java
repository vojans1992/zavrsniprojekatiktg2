package com.iktpreobuka.zavrsni.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.security.Views;

@Entity
@Table(name = "final_grade")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class FinalGradeEntity {

	@JsonView(Views.Admin.class)
	@EmbeddedId
	@Column(name = "final_grade_id")
	private FinalGradeKey id = new FinalGradeKey();
	
	@JsonView(Views.Private.class)
	@Column(name = "value", columnDefinition = "integer default 0")
	private Integer value;
	
	@JsonView(Views.Private.class)
	@NotNull(message = "Final grade must be connected to a semester.")
	@Column(name = "semester", nullable = false)
	private Semester semester;
	
	@JsonView(Views.Private.class)
	@NotNull(message = "Final grade must be connected to a pupil.")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@MapsId("pupilId")
	@JoinColumn (name= "pupil_id", nullable = false)
	private PupilEntity pupil;
	
	@JsonView(Views.Private.class)
	@NotNull(message = "Final grade must be connected to a subject.")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@MapsId("subjectId")
	@JoinColumn (name= "subject_id", nullable = false)
	private SubjectEntity subject;
	
	public FinalGradeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FinalGradeKey getId() {
		return id;
	}

	public void setId(FinalGradeKey id) {
		this.id = id;
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

	public PupilEntity getPupil() {
		return pupil;
	}

	public void setPupil(PupilEntity pupil) {
		this.pupil = pupil;
	}

	public SubjectEntity getSubject() {
		return subject;
	}

	public void setSubject(SubjectEntity subject) {
		this.subject = subject;
	}
	
	
	
}
