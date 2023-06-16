package com.iktpreobuka.zavrsni.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.zavrsni.security.IAuthenticationFacade;
import com.iktpreobuka.zavrsni.security.Views;
import com.iktpreobuka.zavrsni.services.TeacherService;
import com.iktpreobuka.zavrsni.services.UserService;

@RestController
@RequestMapping("api/v1/teachers")
@CrossOrigin(origins = "http://localhost:3000")
public class TeacherController {
	
	@Autowired
	private IAuthenticationFacade authenticationFacade;
	
	@Autowired
	private TeacherService teacherService;
	
	

	@Secured("ROLE_TEACHER")
	@JsonView(Views.Private.class)
	@RequestMapping("/allGrades")
	public ResponseEntity<?> myGrades(){
		return new ResponseEntity<>(teacherService.getAllGrades(authenticationFacade.getAuthentication().getName()),HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@JsonView(Views.Private.class)
	@RequestMapping("/allGradesBySubject")
	public ResponseEntity<?> myGradesBySubject(@RequestParam String subjectName){
		return new ResponseEntity<>(teacherService.getAllGradesBySubject(authenticationFacade.getAuthentication().getName(), subjectName),HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@JsonView(Views.Private.class)
	@RequestMapping("/allGradesByPupil")
	public ResponseEntity<?> myGradesByPupil(@RequestParam String pupilEmail){
		return new ResponseEntity<>(teacherService.getAllGradesByPupil(authenticationFacade.getAuthentication().getName(), pupilEmail),HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@JsonView(Views.Private.class)
	@RequestMapping("/allGradesBySubjectAndPupil")
	public ResponseEntity<?> myGradesBySubjectAndPupil(@RequestParam String subjecName, @RequestParam String pupilEmail){
		return new ResponseEntity<>(teacherService.getAllGradesBySubjectAndPupil(authenticationFacade.getAuthentication().getName(), subjecName, pupilEmail),HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@JsonView(Views.Private.class)
	@RequestMapping("/allDepartments")
	public ResponseEntity<?> myDepartments(){
		return new ResponseEntity<>(teacherService.getDepartments(authenticationFacade.getAuthentication().getName()),HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@JsonView(Views.Private.class)
	@RequestMapping("/studentsByDepartment")
	public ResponseEntity<?> studentsByDepartment(@RequestParam int departmentId){
		return new ResponseEntity<>(teacherService.getStudentsByDepartment(authenticationFacade.getAuthentication().getName(), departmentId),HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@JsonView(Views.Private.class)
	@RequestMapping("/allSubjects")
	public ResponseEntity<?> mySubjects(){
		return new ResponseEntity<>(teacherService.getSubjects(authenticationFacade.getAuthentication().getName()),HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@JsonView(Views.Private.class)
	@RequestMapping("/allPupils")
	public ResponseEntity<?> myPupils(){
		return new ResponseEntity<>(teacherService.getPupils(authenticationFacade.getAuthentication().getName()),HttpStatus.OK);
	}
	
	@Secured("ROLE_TEACHER")
	@JsonView(Views.Private.class)
	@RequestMapping("/allDepartmentsAndSubjects")
	public ResponseEntity<?> myDepartmentsAndSubjects(){
		return new ResponseEntity<>(teacherService.getDepartmentsAndSubjects(authenticationFacade.getAuthentication().getName()),HttpStatus.OK);
	}
}
