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

import com.iktpreobuka.zavrsni.entities.DepartmentEntity;
import com.iktpreobuka.zavrsni.entities.dto.DepartmentDto;
import com.iktpreobuka.zavrsni.repositories.DepartmentRepository;
import com.iktpreobuka.zavrsni.services.DepartmentService;

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
		departmentService.saveDepartmentDtoAsDepartmentEntity(newDepartment);
		return new ResponseEntity<>(newDepartment, HttpStatus.OK);
	}
}
