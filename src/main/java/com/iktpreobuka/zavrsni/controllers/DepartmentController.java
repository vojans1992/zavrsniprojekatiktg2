package com.iktpreobuka.zavrsni.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewDepartment(@Valid @RequestBody DepartmentDto newDepartment){
		DepartmentEntity department;
		try {
			department = departmentService.saveDepartmentDtoAsDepartmentEntity(newDepartment);
		} catch (ClassCastException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.BAD_REQUEST.value(),
					e.getMessage()),
					HttpStatus.BAD_REQUEST);
		}catch (NoSuchElementException e) {
			return new ResponseEntity<RESTError>(new RESTError(HttpStatus.NOT_FOUND.value(),
					e.getMessage()),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(department, HttpStatus.OK);
	}
}
