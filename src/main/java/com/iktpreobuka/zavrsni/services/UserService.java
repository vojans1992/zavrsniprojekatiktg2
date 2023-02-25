package com.iktpreobuka.zavrsni.services;

import com.iktpreobuka.zavrsni.entities.ParentEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.TeacherEntity;
import com.iktpreobuka.zavrsni.entities.UserEntity;
import com.iktpreobuka.zavrsni.entities.dto.ParentDto;
import com.iktpreobuka.zavrsni.entities.dto.PupilDto;
import com.iktpreobuka.zavrsni.entities.dto.TeacherDto;
import com.iktpreobuka.zavrsni.entities.dto.UserDto;

public interface UserService {

	UserEntity saveUserDtoAsUserEntity(UserDto userDto);
	ParentEntity saveParentDtoAsParentEntity(ParentDto parentDto);
	TeacherEntity saveTeacherDtoAsTeacherEntity(TeacherDto teacherDto);
	UserEntity findUserById(Integer id);
	String addSubjectToTeacher(int subjectId, int teacherId);
}
