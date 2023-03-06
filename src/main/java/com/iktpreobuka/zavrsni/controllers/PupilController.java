package com.iktpreobuka.zavrsni.controllers;

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
import com.iktpreobuka.zavrsni.security.IAuthenticationFacade;
import com.iktpreobuka.zavrsni.security.Views;
import com.iktpreobuka.zavrsni.services.PupilService;
import com.iktpreobuka.zavrsni.utils.RESTError;

@RestController
@RequestMapping("api/v1/pupils")
public class PupilController {

	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	private PupilService pupilService;
	
	@Secured("ROLE_PUPIL")
	@JsonView(Views.Public.class)
	@RequestMapping("/allGrades")
	public ResponseEntity<?> myGrades(){
		return new ResponseEntity<>(pupilService.findByEmail(authenticationFacade.getAuthentication().getName()).getGrades(),HttpStatus.OK);
	}
	@Secured("ROLE_PUPIL")
	@JsonView(Views.Public.class)
	@RequestMapping("/gradesBySubject")
	public ResponseEntity<?> gradesBySubject(@RequestParam String subjectName){
		return new ResponseEntity<>(pupilService.findGradesBySubject(authenticationFacade.getAuthentication().getName(),subjectName), HttpStatus.OK);
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public RESTError handleNoSuchElementExceptions(NoSuchElementException ex) {
		return new RESTError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	}
}
