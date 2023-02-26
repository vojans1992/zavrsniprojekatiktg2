package com.iktpreobuka.zavrsni.controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.zavrsni.entities.DepartmentEntity;
import com.iktpreobuka.zavrsni.entities.dto.DepartmentDto;
import com.iktpreobuka.zavrsni.repositories.DepartmentRepository;
import com.iktpreobuka.zavrsni.services.DepartmentService;
import com.iktpreobuka.zavrsni.utils.RESTError;

@RestController
@RequestMapping("api/v1/departments")
public class DepartmentController {
	
	@Autowired
	private DepartmentRepository departmentRepository;
	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping
	public ResponseEntity<?> getAllDepartments(){
		return new ResponseEntity<List<DepartmentEntity>>((List<DepartmentEntity>) departmentRepository.findAll(),HttpStatus.OK);
	}

	@RequestMapping("/{id}")
	public ResponseEntity<?> one(@PathVariable int id) {
		return new ResponseEntity<>(departmentService.findById(id), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewDepartment(@Valid @RequestBody DepartmentDto newDepartment){
		DepartmentEntity department;
		try {
			department = departmentService.saveDepartmentDtoAsDepartmentEntity(newDepartment);
		} catch (ClassCastException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(),
					e.getMessage()),
					HttpStatus.BAD_REQUEST);
		} catch (NoSuchElementException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(),
					e.getMessage()),
					HttpStatus.NOT_FOUND);
		} catch (DataIntegrityViolationException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(),
					e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(department, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteDepartment(@PathVariable int id) {
		return new ResponseEntity<>(departmentService.deleteById(id), HttpStatus.OK);
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
