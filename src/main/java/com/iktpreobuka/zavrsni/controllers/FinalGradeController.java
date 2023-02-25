package com.iktpreobuka.zavrsni.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.zavrsni.entities.FinalGradeEntity;
import com.iktpreobuka.zavrsni.entities.FinalGradeKey;
import com.iktpreobuka.zavrsni.entities.dto.FinalGradeDto;
import com.iktpreobuka.zavrsni.repositories.FinalGradeRepository;
import com.iktpreobuka.zavrsni.services.FinalGradeService;

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

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewFinalGrade(@Valid @RequestBody FinalGradeDto newFinalGrade) {
		finalGradeService.saveFinalGradeDtoAsFinalGradeEntity(newFinalGrade);
		return new ResponseEntity<>(newFinalGrade, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFinalGrade(@RequestParam int subjectId, @RequestParam int pupilId) {
		FinalGradeEntity entity = finalGradeRepository.findByPupilIdAndSubjectId(pupilId, subjectId);
		finalGradeRepository.delete(entity);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
