package com.iktpreobuka.zavrsni.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.entities.dto.GradeDto;
import com.iktpreobuka.zavrsni.repositories.GradeRepository;
import com.iktpreobuka.zavrsni.services.GradeService;
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
	public ResponseEntity<?> one(@PathVariable int id) {
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
		}
		return new ResponseEntity<>(grade, HttpStatus.OK);
	}
	
}
