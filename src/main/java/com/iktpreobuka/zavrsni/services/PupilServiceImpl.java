package com.iktpreobuka.zavrsni.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.ParentEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.dto.PupilDto;
import com.iktpreobuka.zavrsni.repositories.PupilRepository;
import com.iktpreobuka.zavrsni.repositories.UserRepository;
@Service
public class PupilServiceImpl implements PupilService{

	@Autowired
	private RoleService roleService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public PupilEntity savePupilDtoAsPupilEntity(PupilDto pupilDto) {
		PupilEntity pupil = new PupilEntity();
		pupil.setName(pupilDto.getName());
		pupil.setLastName(pupilDto.getLastName());
		pupil.setUsername(pupilDto.getUsername());
		pupil.setEmail(pupilDto.getEmail());
		pupil.setPassword(pupilDto.getPassword());
		pupil.setRole(roleService.findById(3));
		pupil.setDepartment(departmentService.findById(pupilDto.getDepartmentId()));
		pupil.setParent((ParentEntity) userRepository.findById(pupilDto.getParentId()).get());
		userRepository.save(pupil);
		return pupil;
	}

	@Override
	public PupilEntity findById(Integer id) {
		// TODO Auto-generated method stub
		return (PupilEntity) userRepository.findById(id).get();
	}
}
