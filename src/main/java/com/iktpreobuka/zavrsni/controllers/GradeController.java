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
import org.springframework.security.access.annotation.Secured;
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

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.entities.dto.GradeDto;
import com.iktpreobuka.zavrsni.repositories.GradeRepository;
import com.iktpreobuka.zavrsni.security.IAuthenticationFacade;
import com.iktpreobuka.zavrsni.security.Views;
import com.iktpreobuka.zavrsni.services.GradeService;
import com.iktpreobuka.zavrsni.utils.CustomDataBindingException;
import com.iktpreobuka.zavrsni.utils.CustomException;
import com.iktpreobuka.zavrsni.utils.RESTError;

@RestController
@RequestMapping("api/v1/grades")
public class GradeController {

	@Autowired
	private GradeRepository gradeRepository;
	@Autowired
	private GradeService gradeService;
	@Autowired
	private IAuthenticationFacade authenticationFacade;

	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping
	public ResponseEntity<?> getAllGrades() {
		return new ResponseEntity<List<GradeEntity>>((List<GradeEntity>) gradeRepository.findAll(), HttpStatus.OK);
	}

	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable int id) {
		try {
			return new ResponseEntity<>(gradeService.findById(id), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	}

	@Secured("ROLE_TEACHER")
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewGrade(@Valid @RequestBody GradeDto newGrade) {
		GradeEntity grade;
		grade = gradeService.saveGradeDtoAsGradeEntity(newGrade, authenticationFacade.getAuthentication().getName());
		return new ResponseEntity<>(grade, HttpStatus.OK);
	}

	@Secured("ROLE_TEACHER")
	@JsonView(Views.Private.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateGrade(@Valid @RequestBody GradeDto newGrade, @PathVariable int id) {
		newGrade.setId(id);
		return addNewGrade(newGrade);
	}

	@Secured("ROLE_TEACHER")
	@JsonView(Views.Private.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteGrade(@PathVariable int id) {
		try {
			return new ResponseEntity<String>(
					gradeService.deleteById(id, authenticationFacade.getAuthentication().getName()), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
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

}
