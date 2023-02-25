package com.iktpreobuka.zavrsni.services;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.ParentEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.RoleEntity;
import com.iktpreobuka.zavrsni.entities.SubjectEntity;
import com.iktpreobuka.zavrsni.entities.TeacherEntity;
import com.iktpreobuka.zavrsni.entities.UserEntity;
import com.iktpreobuka.zavrsni.entities.dto.ParentDto;
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
		UserEntity user;
		if(userDto.getId() != null) {
			user = userRepository.findById(userDto.getId()).get();
		}else {
			user = new UserEntity();
		}
		user.setName(userDto.getName());
		user.setLastName(userDto.getLastName());
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		
		RoleEntity role;
		try {
			role = roleService.findById(userDto.getRoleId());
			user.setRole(role);
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}
		return userRepository.save(user);
	}

	@Override
	public ParentEntity saveParentDtoAsParentEntity(ParentDto parentDto) {
		ParentEntity parent;
		if(parentDto.getId() != null) {
			try {
				parent = (ParentEntity) userRepository.findById(parentDto.getId()).get();
			} catch (ClassCastException e) {
				throw new ClassCastException("User with ID: " + parentDto.getId() + " is not a parent entity.");
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException(e.getMessage());
			}
		}else {
			parent = new ParentEntity();
		}
		
		parent.setName(parentDto.getName());
		parent.setLastName(parentDto.getLastName());
		parent.setUsername(parentDto.getUsername());
		parent.setEmail(parentDto.getEmail());
		parent.setPassword(parentDto.getPassword());
		parent.setRole(roleService.findById(4));
		
		return userRepository.save(parent);
	}

	@Override
	public TeacherEntity saveTeacherDtoAsTeacherEntity(TeacherDto teacherDto) {
		TeacherEntity teacher = new TeacherEntity();
		teacher.setName(teacherDto.getName());
		teacher.setLastName(teacherDto.getLastName());
		teacher.setUsername(teacherDto.getUsername());
		teacher.setEmail(teacherDto.getEmail());
		teacher.setPassword(teacherDto.getPassword());
		RoleEntity role = roleService.findById(2);
		if(role == null) {
			return null;
		}
		teacher.setRole(role);
		userRepository.save(teacher);
		return teacher;
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
	public UserEntity findUserById(Integer id) {
		UserEntity entity;
		try {
			entity = userRepository.findById(id).get();
			return entity;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("User with ID: " + id + " does not exist.");
		}
	}

	
}
