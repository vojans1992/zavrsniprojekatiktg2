package com.iktpreobuka.zavrsni.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.DepartmentEntity;
import com.iktpreobuka.zavrsni.entities.ParentEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.RoleEntity;
import com.iktpreobuka.zavrsni.entities.SubjectEntity;
import com.iktpreobuka.zavrsni.entities.TeacherEntity;
import com.iktpreobuka.zavrsni.entities.UserEntity;
import com.iktpreobuka.zavrsni.entities.dto.ParentDto;
import com.iktpreobuka.zavrsni.entities.dto.PupilDto;
import com.iktpreobuka.zavrsni.entities.dto.TeacherDto;
import com.iktpreobuka.zavrsni.entities.dto.UserDto;
import com.iktpreobuka.zavrsni.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private SubjectService subjectService;
	
	@Override
	public UserEntity saveUserDtoAsUserEntity(UserDto userDto) {
		UserEntity user = new UserEntity();
		user.setName(userDto.getName());
		user.setLastName(userDto.getLastName());
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setRole(roleService.findById(userDto.getRoleId()));
		userRepository.save(user);
		return user;
	}

	@Override
	public ParentEntity saveParentDtoAsParentEntity(ParentDto parentDto) {
		ParentEntity parent = new ParentEntity();
		parent.setName(parentDto.getName());
		parent.setLastName(parentDto.getLastName());
		parent.setUsername(parentDto.getUsername());
		parent.setEmail(parentDto.getEmail());
		parent.setPassword(parentDto.getPassword());
		parent.setRole(roleService.findById(4));
		userRepository.save(parent);
		return parent;
	}

	@Override
	public TeacherEntity saveTeacherDtoAsTeacherEntity(TeacherDto teacherDto) {
		TeacherEntity teacher = new TeacherEntity();
		teacher.setName(teacherDto.getName());
		teacher.setLastName(teacherDto.getLastName());
		teacher.setUsername(teacherDto.getUsername());
		teacher.setEmail(teacherDto.getEmail());
		teacher.setPassword(teacherDto.getPassword());
		teacher.setRole(roleService.findById(2));
		userRepository.save(teacher);
		return teacher;
	}

	@Override
	public TeacherEntity findTeacherById(Integer id) {
		Optional<UserEntity> teacherEntity = userRepository.findById(id);
		if(teacherEntity.isEmpty() || teacherEntity.get().getRole().getId() != 2) {
			return null;
		}
		return (TeacherEntity) teacherEntity.get();
	}

	@Override
	public String addSubjectToTeacher(int subjectId, int teacherId) {
		TeacherEntity teacher = (TeacherEntity) userRepository.findById(teacherId).get();
		SubjectEntity subject = subjectService.findById(subjectId);
		teacher.getSubjects().add(subject);
		userRepository.save(teacher);
		return teacher.getSubjects().toString();
	}

	@Override
	public PupilEntity findPupilById(Integer id) {
		Optional<UserEntity> pupilEntity = userRepository.findById(id);
		if(pupilEntity.isEmpty() || pupilEntity.get().getRole().getId() != 3) {
			return null;
		}
		return (PupilEntity) pupilEntity.get();
	}

	
	
}
