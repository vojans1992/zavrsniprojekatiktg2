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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.entities.FinalGradeEntity;
import com.iktpreobuka.zavrsni.entities.dto.FinalGradeDto;
import com.iktpreobuka.zavrsni.repositories.FinalGradeRepository;
import com.iktpreobuka.zavrsni.security.IAuthenticationFacade;
import com.iktpreobuka.zavrsni.security.Views;
import com.iktpreobuka.zavrsni.services.FinalGradeService;
import com.iktpreobuka.zavrsni.utils.CustomException;
import com.iktpreobuka.zavrsni.utils.CustomPupilEvaluationException;
import com.iktpreobuka.zavrsni.utils.RESTError;

@RestController
@RequestMapping("api/v1/finalGrades")
public class FinalGradeController {

	@Autowired
	private FinalGradeRepository finalGradeRepository;
	@Autowired
	private FinalGradeService finalGradeService;
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping
	public ResponseEntity<?> getAllFinalGrades() {
		return new ResponseEntity<List<FinalGradeEntity>>((List<FinalGradeEntity>) finalGradeRepository.findAll(),
				HttpStatus.OK);
	}

	@JsonView(Views.Private.class)
	@Secured({"ROLE_ADMIN", "ROLE_TEACHER"})
	@RequestMapping(method = RequestMethod.POST, value = "/evaluatePupil")
	public ResponseEntity<?> addNewFinalGrade(@Valid @RequestBody FinalGradeDto newFinalGrade) {
		String user = authenticationFacade.getAuthentication().getName();
		try {
			return new ResponseEntity<String>(finalGradeService.saveFinalGradeDtoAsFinalGradeEntity(newFinalGrade, user),
					HttpStatus.OK);
		} catch (CustomPupilEvaluationException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (CustomException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFinalGrade(@RequestParam int subjectId, @RequestParam int pupilId) {
		FinalGradeEntity entity = finalGradeRepository.findByPupilIdAndSubjectId(pupilId, subjectId);
		finalGradeRepository.delete(entity);
		return new ResponseEntity<>("Final grade deleted.",HttpStatus.OK);
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
