package com.iktpreobuka.zavrsni.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.SubjectEntity;
import com.iktpreobuka.zavrsni.entities.TeacherEntity;
import com.iktpreobuka.zavrsni.entities.dto.GradeDto;
import com.iktpreobuka.zavrsni.repositories.GradeRepository;

@Service
public class GradeServiceImpl implements GradeService {

	@Autowired
	private GradeRepository gradeRepository;
	@Autowired
	private PupilService pupilService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private UserService userService;
	
	@Override
	public GradeEntity findById(Integer id) {
		GradeEntity entity;
		try {
			entity = gradeRepository.findById(id).get();
			return entity;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Grade with id: " + id + " does not exist.");
		}
	}

	@Override
	public GradeEntity saveGradeDtoAsGradeEntity(GradeDto gradeDto) {
		GradeEntity gradeEntity;
		if(gradeDto.getId() != null) {
			gradeEntity = gradeRepository.findById(gradeDto.getId()).get();
		}else {
			gradeEntity = new GradeEntity();
		}
		
		gradeEntity.setSemester(gradeDto.getSemester());
		gradeEntity.setValue(gradeDto.getValue());
		
		PupilEntity pupil;
		try {
			pupil = pupilService.findById(gradeDto.getPupilId());
			gradeEntity.setPupil(pupil);
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}
		
		SubjectEntity subject;
		try {
			subject = subjectService.findById(gradeDto.getSubjectId());
			gradeEntity.setSubject(subject);
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}
		
		TeacherEntity teacher;
		try {
			teacher = (TeacherEntity) userService.findUserById(gradeDto.getTeacherId());
			gradeEntity.setTeacher(teacher);
		} catch (ClassCastException e) {
			throw new ClassCastException("User with ID: " + gradeDto.getTeacherId() + " is not a teacher entity.");
		}catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}
		
		return gradeRepository.save(gradeEntity);
	}

}
