package com.iktpreobuka.zavrsni.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.entities.dto.GradeDto;
import com.iktpreobuka.zavrsni.repositories.GradeRepository;
import com.iktpreobuka.zavrsni.services.GradeService;


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

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewGrade(@Valid @RequestBody GradeDto newGrade){
		gradeService.saveGradeDtoAsGradeEntity(newGrade);
		return new ResponseEntity<>(newGrade, HttpStatus.OK);
	}
}
