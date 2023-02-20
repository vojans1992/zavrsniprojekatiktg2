package com.iktpreobuka.zavrsni.entities.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import com.iktpreobuka.zavrsni.entities.Year;

public class SubjectDto {

	@NotNull(message = "Subject must have a name.")
	private String name;
	@NotNull(message = "Subject must have weekly classload.")
	private Integer classLoad;
	@NotNull(message = "Subject must have a school year it belongs to.")
	private Year year;
	
	public SubjectDto() {
		super();
		// TODO Auto-generated constructor stub
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
	
	
}
