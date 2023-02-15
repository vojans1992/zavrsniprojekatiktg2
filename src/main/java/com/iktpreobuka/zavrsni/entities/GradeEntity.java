package com.iktpreobuka.zavrsni.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "grade")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class GradeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "grade_id")
	private Integer id;
	@Column(name = "value", columnDefinition = "integer default 0")
	@Min(1)
	@Max(5)
	@NotNull
	private Integer value;
	@NotNull(message = "Grade must be connected to a semester.")
	@Column(name = "semester", nullable = false)
	private Semester semester;
	@NotNull(message = "Grade must be connected to a pupil.")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn (name= "pupil", nullable = false)
	private PupilEntity pupil;
	@NotNull(message = "Grade must be connected to a teacher.")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn (name= "teacher", nullable = false)
	private TeacherEntity teacher;
	@NotNull(message = "Grade must be connected to a subject.")
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn (name= "subject", nullable = false)
	private SubjectEntity subject;
	
	public GradeEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GradeEntity(Integer id, @Min(1) @Max(5) @NotNull Integer value, @NotNull Semester semester,
			@NotNull PupilEntity pupil, @NotNull TeacherEntity teacher, @NotNull SubjectEntity subject) {
		super();
		this.id = id;
		this.value = value;
		this.semester = semester;
		this.pupil = pupil;
		this.teacher = teacher;
		this.subject = subject;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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
	
	
	
	
}
