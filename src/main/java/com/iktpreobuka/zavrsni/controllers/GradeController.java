package com.iktpreobuka.zavrsni.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.entities.dto.GradeDto;
import com.iktpreobuka.zavrsni.repositories.GradeRepository;
import com.iktpreobuka.zavrsni.services.GradeService;
import com.iktpreobuka.zavrsni.utils.CustomDataBindingException;
import com.iktpreobuka.zavrsni.utils.RESTError;


@RestController
@RequestMapping("api/v1/grades")
public class GradeController {

	@Autowired
	private GradeRepository gradeRepository;
	@Autowired
	private GradeService gradeService;
	
	@RequestMapping
	public ResponseEntity<?> getAllGrades(){
		return new ResponseEntity<List<GradeEntity>>((List<GradeEntity>) gradeRepository.findAll(),HttpStatus.OK);
	}

	@RequestMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable int id) {
		try {
			return new ResponseEntity<>(gradeService.findById(id), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(),
					e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewGrade(@Valid @RequestBody GradeDto newGrade){
		GradeEntity grade;
		try {
			grade = gradeService.saveGradeDtoAsGradeEntity(newGrade);
		} catch (ClassCastException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(),
					e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}catch (NoSuchElementException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(),
					e.getMessage()),
					HttpStatus.NOT_FOUND);
		}catch (CustomDataBindingException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(),
					e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(grade, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteGrade(@PathVariable int id){
		try {
			return new ResponseEntity<String>(gradeService.deleteById(id), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(),
					e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = "";
			String errorMessage = "";
			if (error instanceof FieldError) {
				fieldName = ((FieldError) error).getField();
				errorMessage = error.getDefaultMessage();
			} else if (error instanceof ObjectError) {
				fieldName = ((ObjectError) error).getObjectName();
				errorMessage = error.getDefaultMessage();
			}
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public RESTError handleEmptyResultDataAccessExceptions(EmptyResultDataAccessException ex) {
		return new RESTError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	}
}
