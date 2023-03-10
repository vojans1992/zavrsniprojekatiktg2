package com.iktpreobuka.zavrsni.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.DepartmentEntity;
import com.iktpreobuka.zavrsni.entities.TeacherEntity;
import com.iktpreobuka.zavrsni.entities.Year;
import com.iktpreobuka.zavrsni.entities.dto.DepartmentDto;
import com.iktpreobuka.zavrsni.repositories.DepartmentRepository;

@Service
public class DepartmentServiceImpl implements DepartmentService {

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

		if (departmentDto.getId() != null) {
			try {
				departmentEntity = departmentRepostitory.findById(departmentDto.getId()).get();
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException("Department with ID: " + departmentDto.getId() + " does not exist.");
			}
		} else {
			departmentEntity = new DepartmentEntity();
		}

		departmentEntity.setName(departmentDto.getName());

		if (departmentDto.getYear() == null) {
			departmentEntity.setYear(Year.FIRST_YEAR);
		} else {
			departmentEntity.setYear(Year.values()[departmentDto.getYear()]);
		}

		TeacherEntity homeroomTeacher;
		try {
			homeroomTeacher = (TeacherEntity) userService.findUserById(departmentDto.getHomeroomTeacherId());
			departmentEntity.setHomeroomTeacher(homeroomTeacher);
		} catch (ClassCastException e) {
			throw new ClassCastException(
					"User with ID: " + departmentDto.getHomeroomTeacherId() + " is not a teacher entity.");
		}
		try {
			return departmentRepostitory.save(departmentEntity);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityViolationException(e.getCause().getCause().getMessage());
		}
	}

	@Override
	public String deleteById(Integer id) {
		try {
			departmentRepostitory.deleteById(id);
			return "Deleted department with ID: " + id;
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("Department with ID: " + id + " does not exist.", 1);
		}
	}

}
