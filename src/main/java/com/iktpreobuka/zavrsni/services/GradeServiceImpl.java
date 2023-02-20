package com.iktpreobuka.zavrsni.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.GradeEntity;
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
		// TODO Auto-generated method stub
		return gradeRepository.findById(id).get();
	}

	@Override
	public GradeEntity saveGradeDtoAsGradeEntity(GradeDto gradeDto) {
		GradeEntity gradeEntity = new GradeEntity();
		gradeEntity.setSemester(gradeDto.getSemester());
		gradeEntity.setValue(gradeDto.getValue());
		gradeEntity.setPupil(pupilService.findById(gradeDto.getPupilId()));
		gradeEntity.setSubject(subjectService.findById(gradeDto.getSubjectId()));
		gradeEntity.setTeacher(userService.findTeacherById(gradeDto.getTeacherId()));
		
		gradeRepository.save(gradeEntity);
		return gradeEntity;
	}

}
