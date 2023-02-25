package com.iktpreobuka.zavrsni.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.DepartmentEntity;
import com.iktpreobuka.zavrsni.entities.TeacherEntity;
import com.iktpreobuka.zavrsni.entities.Year;
import com.iktpreobuka.zavrsni.entities.dto.DepartmentDto;
import com.iktpreobuka.zavrsni.repositories.DepartmentRepository;
@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository departmentRepostitory;
	@Autowired
	private UserService userService;
	
	@Override
	public DepartmentEntity findById(Integer id) {
		DepartmentEntity entity;
		try {
			entity = departmentRepostitory.findById(id).get();
			return entity;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Department with id: " + id + " does not exist.");
		}
	}

	@Override
	public DepartmentEntity saveDepartmentDtoAsDepartmentEntity(DepartmentDto departmentDto) {
		DepartmentEntity departmentEntity;
		
		if(departmentDto.getId() != null) {
			departmentEntity= departmentRepostitory.findById(departmentDto.getId()).get();
		}else {
			departmentEntity = new DepartmentEntity();
		}
		
		if(departmentDto.getYear() == null) {
			departmentEntity.setYear(Year.FIRST_YEAR);
		}else {
			departmentEntity.setYear(departmentDto.getYear());
		}
		
		TeacherEntity homeroomTeacher;
		try {
			homeroomTeacher = (TeacherEntity) userService.findUserById(departmentDto.getHomeroomTeacherId());
			departmentEntity.setHomeroomTeacher(homeroomTeacher);
		} catch (ClassCastException e) {
			throw new ClassCastException("User with ID: " + departmentDto.getHomeroomTeacherId() + " is not a teacher entity.");
		}catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}
		
		
		return departmentRepostitory.save(departmentEntity);
	}

}
