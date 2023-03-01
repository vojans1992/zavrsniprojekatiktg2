package com.iktpreobuka.zavrsni.entities.dto;

import javax.validation.constraints.NotNull;

public class EmailDto {

	@NotNull(message = "email must have a recipient")
	private String to;
	@NotNull(message = "email must have a subject")
	private String subject;
	@NotNull(message = "email must have text")
	private String text;
	
	public EmailDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public EmailDto(String to, String subject, String text) {
		super();
		this.to = to;
		this.subject = subject;
		this.text = text;
	}

	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
