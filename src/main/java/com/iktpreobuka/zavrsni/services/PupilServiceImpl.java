package com.iktpreobuka.zavrsni.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.DepartmentEntity;
import com.iktpreobuka.zavrsni.entities.ParentEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.dto.PupilDto;
import com.iktpreobuka.zavrsni.repositories.PupilRepository;
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
				pupil = (PupilEntity) pupilRepository.findById(pupilDto.getId()).get();
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
		pupil.setUsername(pupilDto.getUsername());
		pupil.setEmail(pupilDto.getEmail());
		pupil.setPassword(pupilDto.getPassword());
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
			throw new NoSuchElementException("Pupil with id: " + id + " does not exist.");
		}
	}
}
