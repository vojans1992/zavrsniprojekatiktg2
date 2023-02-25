package com.iktpreobuka.zavrsni.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.zavrsni.entities.dto.SubjectDto;
import com.iktpreobuka.zavrsni.services.SubjectService;

@RestController
@RequestMapping(value = "api/v1/subjects")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addSubject(@Validated @RequestBody SubjectDto newSubject){
		return new ResponseEntity<>(subjectService.saveSubjectDtoAsSubjectEntity(newSubject), HttpStatus.OK);
	}
}
