package com.iktpreobuka.zavrsni.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.entities.FinalGradeEntity;
import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.security.IAuthenticationFacade;
import com.iktpreobuka.zavrsni.security.Views;
import com.iktpreobuka.zavrsni.services.ParentService;
import com.iktpreobuka.zavrsni.utils.CustomException;
import com.iktpreobuka.zavrsni.utils.RESTError;

@RestController
@RequestMapping("api/v1/parents")
public class ParentController {

	@Autowired
	private IAuthenticationFacade authenticationFacade;
	@Autowired
	private ParentService parentService;
	
	@Secured("ROLE_PARENT")
	@JsonView(Views.Public.class)
	@RequestMapping("/allGrades")
	public ResponseEntity<?> myChildGrades(@RequestParam String childEmail){
		try {
			return new ResponseEntity<List<GradeEntity>>(parentService.getChildGrades(authenticationFacade.getAuthentication().getName(), childEmail), HttpStatus.OK);
		} catch (CustomException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@Secured("ROLE_PARENT")
	@JsonView(Views.Public.class)
	@RequestMapping("/finalGrades")
	public ResponseEntity<?> myChildFinalGrades(@RequestParam String childEmail){
		try {
			return new ResponseEntity<List<FinalGradeEntity>>(parentService.getChildFinalGrades(authenticationFacade.getAuthentication().getName(), childEmail), HttpStatus.OK);
		} catch (CustomException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@Secured("ROLE_PARENT")
	@JsonView(Views.Public.class)
	@RequestMapping("/gradesBySubject")
	public ResponseEntity<?> myChildGradesBySubject(@RequestParam String childEmail, @RequestParam String subjectName){
		try {
			return new ResponseEntity<List<GradeEntity>>(parentService.getChildGradesBySubject(authenticationFacade.getAuthentication().getName(), childEmail, subjectName), HttpStatus.OK);
		} catch (CustomException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
}
