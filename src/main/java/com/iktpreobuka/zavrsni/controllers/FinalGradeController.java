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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.zavrsni.entities.FinalGradeEntity;
import com.iktpreobuka.zavrsni.entities.FinalGradeKey;
import com.iktpreobuka.zavrsni.entities.dto.FinalGradeDto;
import com.iktpreobuka.zavrsni.repositories.FinalGradeRepository;
import com.iktpreobuka.zavrsni.services.FinalGradeService;
import com.iktpreobuka.zavrsni.utils.CustomPupilEvaluationException;
import com.iktpreobuka.zavrsni.utils.RESTError;

@RestController
@RequestMapping("api/v1/finalGrades")
public class FinalGradeController {

	@Autowired
	private FinalGradeRepository finalGradeRepository;
	@Autowired
	private FinalGradeService finalGradeService;

	@RequestMapping
	public ResponseEntity<?> getAllFinalGrades() {
		return new ResponseEntity<List<FinalGradeEntity>>((List<FinalGradeEntity>) finalGradeRepository.findAll(),
				HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/evaluatePupil")
	public ResponseEntity<?> addNewFinalGrade(@Valid @RequestBody FinalGradeDto newFinalGrade) {
		try {
			return new ResponseEntity<String>(finalGradeService.saveFinalGradeDtoAsFinalGradeEntity(newFinalGrade),
					HttpStatus.OK);
		} catch (CustomPupilEvaluationException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFinalGrade(@RequestParam int subjectId, @RequestParam int pupilId) {
		FinalGradeEntity entity = finalGradeRepository.findByPupilIdAndSubjectId(pupilId, subjectId);
		finalGradeRepository.delete(entity);
		return new ResponseEntity<>(HttpStatus.OK);
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

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ClassCastException.class)
	public RESTError handleClassCastExceptions(ClassCastException ex) {
		return new RESTError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoSuchElementException.class)
	public RESTError handleNoSuchElementExceptions(NoSuchElementException ex) {
		return new RESTError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public RESTError handleEmptyResultDataAccessExceptions(EmptyResultDataAccessException ex) {
		return new RESTError(HttpStatus.NOT_FOUND.value(), ex.getMessage());
	}
}
