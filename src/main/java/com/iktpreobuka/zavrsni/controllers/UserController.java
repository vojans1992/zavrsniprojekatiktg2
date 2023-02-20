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

import com.iktpreobuka.zavrsni.entities.DepartmentEntity;
import com.iktpreobuka.zavrsni.entities.UserEntity;
import com.iktpreobuka.zavrsni.entities.dto.ParentDto;
import com.iktpreobuka.zavrsni.entities.dto.PupilDto;
import com.iktpreobuka.zavrsni.entities.dto.TeacherDto;
import com.iktpreobuka.zavrsni.entities.dto.UserDto;
import com.iktpreobuka.zavrsni.repositories.UserRepository;
import com.iktpreobuka.zavrsni.services.PupilService;
import com.iktpreobuka.zavrsni.services.UserService;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private PupilService pupilService;

	@RequestMapping()
	public ResponseEntity<?> listaUsera() {
		return new ResponseEntity<List<UserEntity>>((List<UserEntity>) userRepository.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewUser(@Valid @RequestBody UserDto newUser) {
		userService.saveUserDtoAsUserEntity(newUser);
		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pupils", method = RequestMethod.POST)
	public ResponseEntity<?> addNewUser(@Valid @RequestBody PupilDto newPupil) {
		pupilService.savePupilDtoAsPupilEntity(newPupil);
		return new ResponseEntity<>(newPupil, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/parents", method = RequestMethod.POST)
	public ResponseEntity<?> addNewUser(@Valid @RequestBody ParentDto newParent) {
		userService.saveParentDtoAsParentEntity(newParent);
		return new ResponseEntity<>(newParent, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/teachers", method = RequestMethod.POST)
	public ResponseEntity<?> addNewUser(@Valid @RequestBody TeacherDto newTeacher) {
		userService.saveTeacherDtoAsTeacherEntity(newTeacher);
		return new ResponseEntity<>(newTeacher, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/teachers/addSubject", method = RequestMethod.POST)
	public ResponseEntity<?> addSubjectToTeacher(@RequestParam int subjectId, @RequestParam int teacherId){
		userService.addSubjectToTeacher(subjectId, teacherId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
}
