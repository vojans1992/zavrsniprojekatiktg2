package com.iktpreobuka.zavrsni.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.zavrsni.entities.SubjectEntity;
import com.iktpreobuka.zavrsni.entities.dto.SubjectDto;
import com.iktpreobuka.zavrsni.repositories.SubjectRepository;
import com.iktpreobuka.zavrsni.services.SubjectService;
import com.iktpreobuka.zavrsni.utils.RESTError;

@RestController
@RequestMapping(value = "api/v1/subjects")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;
	@Autowired
	private SubjectRepository subjectRepository;
	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping
	public ResponseEntity<?> getAllSubjects(){
		logger.info("GetAllSubjects invoked.");
		return new ResponseEntity<List<SubjectEntity>>((List<SubjectEntity>) subjectRepository.findAll(),HttpStatus.OK);
	}
	
	@RequestMapping("/{id}")
	public ResponseEntity<?> one(@PathVariable int id) {
		logger.info("one invoked.");
		try {
			return new ResponseEntity<>(subjectService.findById(id), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			logger.error("Exception occured in method one, message: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(),
					e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addSubject(@Validated @RequestBody SubjectDto newSubject){
		logger.info("addSubject invoked.");
		SubjectEntity subjectEntity;
		try {
			subjectEntity = subjectService.saveSubjectDtoAsSubjectEntity(newSubject);
		} catch (NoSuchElementException e) {
			logger.error("Exception occured in method addSubject, message: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(),
					e.getMessage()),
					HttpStatus.NOT_FOUND);
		} 
		return new ResponseEntity<>(subjectEntity, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateSubject(@Validated @RequestBody SubjectDto newSubject, @PathVariable int id){
		logger.info("updateSubject invoked.");
		newSubject.setId(id);
		return addSubject(newSubject);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteSubject(@PathVariable int id) {
		logger.info("deleteSubject invoked.");
		try {
			return new ResponseEntity<>(subjectService.deleteById(id), HttpStatus.OK);
		} catch (EmptyResultDataAccessException e) {
			logger.error("Exception occured in method deleteSubject, message: " + e.getMessage());
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(),
					e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		logger.info("handleValidationExceptions invoked.");
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = "";
			String errorMessage = "";
			if (error instanceof FieldError) {
				fieldName = ((FieldError) error).getField();
				errorMessage = error.getDefaultMessage();
			}
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}
}
