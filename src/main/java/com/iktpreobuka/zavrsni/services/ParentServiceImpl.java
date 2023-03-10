package com.iktpreobuka.zavrsni.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.FinalGradeEntity;
import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.entities.ParentEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.utils.CustomException;

@Service
public class ParentServiceImpl implements ParentService {
	
	@Autowired
	private UserService userService;
	@Autowired
	private PupilService pupilService;

	@Override
	public List<GradeEntity> getChildGrades(String parentEmail, String childEmail) {
		ParentEntity parent = (ParentEntity) userService.findUserByEmail(parentEmail);
		PupilEntity pupil = pupilService.findByEmail(childEmail);
		
		for (PupilEntity child : parent.getChildren()) {
			if(child.getId() == pupil.getId()) {
				return pupil.getGrades();
			}
		}
		throw new CustomException("Pupil " + pupil.getName() + " " + pupil.getLastName() + " is not a child of " + 
				parent.getName() + " " + parent.getLastName());
	}

	@Override
	public List<GradeEntity> getChildGradesBySubject(String parentEmail, String childEmail, String subjectName) {
		ParentEntity parent = (ParentEntity) userService.findUserByEmail(parentEmail);
		PupilEntity pupil = pupilService.findByEmail(childEmail);
		
		for (PupilEntity child : parent.getChildren()) {
			if(child.getId() == pupil.getId()) {
				return pupilService.findGradesBySubject(childEmail, subjectName);
			}
		}
		throw new CustomException("Pupil " + pupil.getName() + " " + pupil.getLastName() + " is not a child of " + 
				parent.getName() + " " + parent.getLastName());
	}

	@Override
	public List<FinalGradeEntity> getChildFinalGrades(String name, String childEmail) {
		ParentEntity parent = (ParentEntity) userService.findUserByEmail(name);
		PupilEntity pupil = pupilService.findByEmail(childEmail);
		
		for (PupilEntity child : parent.getChildren()) {
			if(child.getId() == pupil.getId()) {
				return child.getFinalGrades();
			}
		}
		throw new CustomException("Pupil " + pupil.getName() + " " + pupil.getLastName() + " is not a child of " + 
				parent.getName() + " " + parent.getLastName());
	}

}
