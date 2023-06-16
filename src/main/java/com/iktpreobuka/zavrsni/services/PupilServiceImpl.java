package com.iktpreobuka.zavrsni.services;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.DepartmentEntity;
import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.entities.ParentEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.dto.PupilDto;
import com.iktpreobuka.zavrsni.repositories.PupilRepository;
import com.iktpreobuka.zavrsni.utils.Encryption;
@Service
public class PupilServiceImpl implements PupilService{

	@Autowired
	private RoleService roleService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private PupilRepository pupilRepository;
	@Autowired
	private UserService userService;
	
	@Override
	public PupilEntity savePupilDtoAsPupilEntity(PupilDto pupilDto) {
		PupilEntity pupil;
		if(pupilDto.getId() != null) {
			try {
				pupil = (PupilEntity) findById(pupilDto.getId());
			} catch (ClassCastException e) {
				throw new ClassCastException("User with ID: " + pupilDto.getId() + " is not a pupil entity.");
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException(e.getMessage());
			}
		}else {
			pupil = new PupilEntity();
		}
		
		pupil.setName(pupilDto.getName());
		pupil.setLastName(pupilDto.getLastName());
		pupil.setEmail(pupilDto.getEmail());
		pupil.setPassword(Encryption.getPassEncoded(pupilDto.getPassword()));
		pupil.setRole(roleService.findById(3));
		
		DepartmentEntity department;
		try {
			department = departmentService.findById(pupilDto.getDepartmentId());
			pupil.setDepartment(department);
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}
		
		ParentEntity parent;
		try {
			parent = (ParentEntity) userService.findUserById(pupilDto.getParentId());
			pupil.setParent(parent);
		} catch (ClassCastException e) {
			throw new ClassCastException("User with ID: " + pupilDto.getParentId() + " is not a parent entity.");
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}
		
		return pupilRepository.save(pupil);
	}

	@Override
	public PupilEntity findById(Integer id) {
		PupilEntity entity;
		try {
			entity = pupilRepository.findById(id).get();
			return entity;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Pupil with ID: " + id + " does not exist.");
		}
	}
	
	@Override
	public PupilEntity findByEmail(String email) {
		PupilEntity entity;
		try {
			entity = pupilRepository.findByEmail(email).get();
			return entity;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Pupil with email: " + email + " does not exist.");
		}
	}
	
	@Override
	public List<GradeEntity> findGradesBySubject(String email, String subjectName) {
		PupilEntity pupil = findByEmail(email);
		List<GradeEntity> grades = new ArrayList<>();
		for (GradeEntity gradeEntity : pupil.getGrades()) {
			if(gradeEntity.getSubject().getName().equals(subjectName)) {
				grades.add(gradeEntity);
			}
		}
		return grades;
	}
	
}
