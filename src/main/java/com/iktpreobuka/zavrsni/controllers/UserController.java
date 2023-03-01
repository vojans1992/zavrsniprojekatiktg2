package com.iktpreobuka.zavrsni.controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.zavrsni.entities.ParentEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.TeacherEntity;
import com.iktpreobuka.zavrsni.entities.UserEntity;
import com.iktpreobuka.zavrsni.entities.dto.ParentDto;
import com.iktpreobuka.zavrsni.entities.dto.PupilDto;
import com.iktpreobuka.zavrsni.entities.dto.TeacherDto;
import com.iktpreobuka.zavrsni.entities.dto.UserDto;
import com.iktpreobuka.zavrsni.repositories.UserRepository;
import com.iktpreobuka.zavrsni.services.PupilService;
import com.iktpreobuka.zavrsni.services.UserService;
import com.iktpreobuka.zavrsni.utils.RESTError;
import com.iktpreobuka.zavrsni.utils.UserCustomValidation;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private PupilService pupilService;
	@Autowired
	private UserCustomValidation userValidator;

	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(userValidator);
	}

	@RequestMapping()
	public ResponseEntity<?> list() {
		return new ResponseEntity<List<UserEntity>>((List<UserEntity>) userRepository.findAll(), HttpStatus.OK);
	}

	@RequestMapping("/{id}")
	public ResponseEntity<?> one(@PathVariable int id) {
		return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewUser(@Valid @RequestBody UserDto newUser) {
		return new ResponseEntity<>(userService.saveUserDtoAsUserEntity(newUser), HttpStatus.OK);
	}

	@RequestMapping(value = "/pupils", method = RequestMethod.POST)
	public ResponseEntity<?> addNewPupil(@Valid @RequestBody PupilDto newPupil) {
		return new ResponseEntity<>(pupilService.savePupilDtoAsPupilEntity(newPupil), HttpStatus.OK);
	}

	@RequestMapping(value = "/parents", method = RequestMethod.POST)
	public ResponseEntity<?> addNewParent(@Valid @RequestBody ParentDto newParent) {
		return new ResponseEntity<>(userService.saveParentDtoAsParentEntity(newParent), HttpStatus.OK);
	}

	@RequestMapping(value = "/teachers", method = RequestMethod.POST)
	public ResponseEntity<?> addNewTeacher(@Valid @RequestBody TeacherDto newTeacher) {
		return new ResponseEntity<>(userService.saveTeacherDtoAsTeacherEntity(newTeacher), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto newUser, @PathVariable int id) {
		newUser.setId(id);
		return addNewUser(newUser);
	}

	@RequestMapping(value = "/pupils/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updatePupil(@Valid @RequestBody PupilDto newPupil, @PathVariable int id) {
		newPupil.setId(id);
		return addNewPupil(newPupil);
	}

	@RequestMapping(value = "/parents/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateParent(@Valid @RequestBody ParentDto newParent, @PathVariable int id) {
		newParent.setId(id);
		return addNewParent(newParent);
	}

	@RequestMapping(value = "/teachers/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateTeacher(@Valid @RequestBody TeacherDto newTeacher, @PathVariable int id) {
		newTeacher.setId(id);
		return addNewTeacher(newTeacher);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteUser(@PathVariable int id) {
		return new ResponseEntity<>(userService.deleteById(id), HttpStatus.OK);
	}

	@RequestMapping(value = "/teachers/addSubject", method = RequestMethod.POST)
	public ResponseEntity<?> addSubjectToTeacher(@RequestParam int subjectId, @RequestParam int teacherId) {
		return new ResponseEntity<>(userService.addSubjectToTeacher(subjectId, teacherId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/teachers/removeSubject", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeSubjectFromTeacher(@RequestParam int subjectId, @RequestParam int teacherId) {
		return new ResponseEntity<>(userService.removeSubjectFromTeacher(subjectId, teacherId), HttpStatus.OK);
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
