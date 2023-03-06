package com.iktpreobuka.zavrsni.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.ParentEntity;
import com.iktpreobuka.zavrsni.entities.RoleEntity;
import com.iktpreobuka.zavrsni.entities.TeacherEntity;
import com.iktpreobuka.zavrsni.entities.UserEntity;
import com.iktpreobuka.zavrsni.entities.dto.ParentDto;
import com.iktpreobuka.zavrsni.entities.dto.TeacherDto;
import com.iktpreobuka.zavrsni.entities.dto.UserDto;
import com.iktpreobuka.zavrsni.repositories.UserRepository;
import com.iktpreobuka.zavrsni.utils.Encryption;

@Service
public class UserServiceImpl implements UserService{
	

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleService roleService;
	
	@Override
	public UserEntity saveUserDtoAsUserEntity(UserDto userDto) {
		UserEntity user;
		if(userDto.getId() != null) {
			try {
				user = userRepository.findById(userDto.getId()).get();
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException("User with id: " + userDto.getId()+ " does not exist.");
			}
			
		}else {
			user = new UserEntity();
		}
		user.setName(userDto.getName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		user.setPassword(Encryption.getPassEncoded(userDto.getPassword()));
		
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
				parent = (ParentEntity) findUserById(parentDto.getId());
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
		parent.setEmail(parentDto.getEmail());
		parent.setPassword(Encryption.getPassEncoded(parentDto.getPassword()));
		parent.setRole(roleService.findById(4));
		
		return userRepository.save(parent);
	}

	@Override
	public TeacherEntity saveTeacherDtoAsTeacherEntity(TeacherDto teacherDto) {
		TeacherEntity teacher;
		if(teacherDto.getId() != null) {
			try {
				teacher = (TeacherEntity) findUserById(teacherDto.getId());
			} catch (ClassCastException e) {
				throw new ClassCastException("User with ID: " + teacherDto.getId() + " is not a teacher entity.");
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException(e.getMessage());
			}
		}else {
			teacher = new TeacherEntity();
		}
		
		teacher.setName(teacherDto.getName());
		teacher.setLastName(teacherDto.getLastName());
		teacher.setEmail(teacherDto.getEmail());
		teacher.setPassword(Encryption.getPassEncoded(teacherDto.getPassword()));
		teacher.setRole(roleService.findById(2));
		
		return userRepository.save(teacher);
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
	
	@Override
	public UserEntity findUserByEmail(String email) {
		UserEntity entity;
		try {
			entity = userRepository.findByEmail(email).get();
			return entity;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("User with email: " + email + " does not exist.");
		}
	}

	@Override
	public String deleteById(Integer id) {
		try {
			userRepository.deleteById(id);
			return "Deleted user with ID: " + id;
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("User with ID: " + id + " does not exist.", 1);
		}
		
	}
	
}
