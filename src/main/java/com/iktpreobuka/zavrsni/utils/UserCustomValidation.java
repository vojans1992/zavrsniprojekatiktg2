package com.iktpreobuka.zavrsni.utils;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.iktpreobuka.zavrsni.entities.dto.ParentDto;
import com.iktpreobuka.zavrsni.entities.dto.PupilDto;
import com.iktpreobuka.zavrsni.entities.dto.TeacherDto;
import com.iktpreobuka.zavrsni.entities.dto.UserDto;

@Component
public class UserCustomValidation implements Validator {

	@Override
	public boolean supports(Class<?> myClass) {
		return UserDto.class.equals(myClass) || ParentDto.class.equals(myClass) 
				|| TeacherDto.class.equals(myClass) || PupilDto.class.equals(myClass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if(target.getClass() == TeacherDto.class) {
			TeacherDto teacher = (TeacherDto) target;
			if (!teacher.getPassword().equals(teacher.getConfirmedPassword())) {
				errors.reject("400", "Passwords must be the same");
			}
		}
		if(target.getClass() == UserDto.class) {
			UserDto user = (UserDto) target;
			if (!user.getPassword().equals(user.getConfirmedPassword())) {
				errors.reject("400", "Passwords must be the same");
			}
		}
		if(target.getClass() == PupilDto.class) {
			PupilDto pupil = (PupilDto) target;
			if (!pupil.getPassword().equals(pupil.getConfirmedPassword())) {
				errors.reject("400", "Passwords must be the same");
			}
		}
		if(target.getClass() == ParentDto.class) {
			ParentDto parent = (ParentDto) target;
			if (!parent.getPassword().equals(parent.getConfirmedPassword())) {
				errors.reject("400", "Passwords must be the same");
			}
		}
	}
}
