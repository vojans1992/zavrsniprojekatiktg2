package com.iktpreobuka.zavrsni.entities.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.iktpreobuka.zavrsni.entities.Year;

public class DepartmentDto {
	
	private Integer id;
	@NotNull(message = "Department belong to a school year")
	@Min(value = 0, message = "Departments can only belong to a year in ragne from 0 to 7")
	@Max(value = 7, message = "Departments can only belong to a year in ragne from 0 to 7")
	private Integer year;
	@NotNull(message = "Department must have a name")
	private String name;
	@NotNull(message = "Homeroom teacher id must be provided")
	private Integer homeroomTeacherId;
	
	public DepartmentDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getHomeroomTeacherId() {
		return homeroomTeacherId;
	}

	public void setHomeroomTeacherId(Integer homeroomTeacherId) {
		this.homeroomTeacherId = homeroomTeacherId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
