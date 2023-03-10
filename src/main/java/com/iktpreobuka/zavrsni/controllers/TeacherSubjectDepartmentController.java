package com.iktpreobuka.zavrsni.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.entities.TeacherSubjectDepartmentEntity;
import com.iktpreobuka.zavrsni.repositories.TeacherSubjectDepartmentRepository;
import com.iktpreobuka.zavrsni.security.Views;
import com.iktpreobuka.zavrsni.services.TeacherSubjectDepartmentService;
import com.iktpreobuka.zavrsni.utils.RESTError;

@RestController
@RequestMapping("api/v1/teacherSubjectDepartment")
public class TeacherSubjectDepartmentController {

	@Autowired
	private TeacherSubjectDepartmentRepository teacherSubjectDepartmentRepository;
	@Autowired
	private TeacherSubjectDepartmentService teacherSubjectDepartmentService;
	
	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping
	public ResponseEntity<?> getAll() {
		return new ResponseEntity<List<TeacherSubjectDepartmentEntity>>((List<TeacherSubjectDepartmentEntity>) teacherSubjectDepartmentRepository.findAll(), HttpStatus.OK);
	}
	
	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewTSD(@RequestParam int teacherId, @RequestParam int subjectId, @RequestParam int departmentId){
		try {
			TeacherSubjectDepartmentEntity tSD =  teacherSubjectDepartmentService.save(teacherId, subjectId, departmentId);
			return new ResponseEntity<>(tSD, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
	
	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping("/getOne")
	public ResponseEntity<?> getOne(@RequestParam int teacherId, @RequestParam int subjectId, @RequestParam int departmentId){
		try {
			return new ResponseEntity<>(teacherSubjectDepartmentService.findById(teacherId, subjectId, departmentId), HttpStatus.OK);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(),
					e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
	}
	
	@Secured("ROLE_ADMIN")
	@JsonView(Views.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteOne")
	public ResponseEntity<?> deteleTSD(@RequestParam int teacherId, @RequestParam int subjectId, @RequestParam int departmentId){
		try {
			teacherSubjectDepartmentService.delete(teacherId, subjectId, departmentId);
			String string = "Deleted TSD " + teacherId + subjectId + departmentId;
			return new ResponseEntity<String>(string, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST);
		}
	}
}
