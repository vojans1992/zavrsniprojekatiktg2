package com.iktpreobuka.zavrsni.utils;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.security.Views;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@JsonView(Views.Admin.class)
	@ExceptionHandler({ClassCastException.class, NoSuchElementException.class, EmptyResultDataAccessException.class,
		SQLIntegrityConstraintViolationException.class, MethodArgumentTypeMismatchException.class, CustomException.class,
		CustomDataBindingException.class, CustomPupilEvaluationException.class, IllegalArgumentException.class})
	public ResponseEntity<?> handleException(Exception e){
		if(e instanceof ClassCastException) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
		} else if(e instanceof NoSuchElementException) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
		} else if(e instanceof EmptyResultDataAccessException) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(), e.getMessage()), HttpStatus.NOT_FOUND);
		} else if(e instanceof SQLIntegrityConstraintViolationException) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
		} else if(e instanceof MethodArgumentTypeMismatchException) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
		} else if(e instanceof CustomException) {  
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
		} else if(e instanceof CustomDataBindingException) {  
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
		} else if(e instanceof CustomPupilEvaluationException) {  
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
		} else if(e instanceof IllegalArgumentException) {  
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		else
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
	}
	
}
