package com.iktpreobuka.zavrsni.entities.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.iktpreobuka.zavrsni.entities.Year;

public class DepartmentDto {
	
	private Year year;
	@NotNull(message = "Homeroom teacher id must be provided")
	private Integer homeroomTeacherId;
	
	public DepartmentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Year getYear() {
		return year;
	}

	public void setYear(Year year) {
		this.year = year;
	}

	public Integer getHomeroomTeacherId() {
		return homeroomTeacherId;
	}

	public void setHomeroomTeacherId(Integer homeroomTeacherId) {
		this.homeroomTeacherId = homeroomTeacherId;
	}

	
}