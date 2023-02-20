package com.iktpreobuka.zavrsni.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.FinalGradeEntity;
import com.iktpreobuka.zavrsni.entities.FinalGradeKey;
import com.iktpreobuka.zavrsni.entities.dto.FinalGradeDto;
import com.iktpreobuka.zavrsni.repositories.FinalGradeRepository;

@Service
public class FinalGradeServiceImpl implements FinalGradeService {

	@Autowired
	private FinalGradeRepository finalGradeRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private SubjectService subjectService;
	
	@Override
	public FinalGradeEntity findById(Integer id) {
		// TODO Auto-generated method stub
		return finalGradeRepository.findById(id).get();
	}

	@Override
	public FinalGradeEntity saveFinalGradeDtoAsFinalGradeEntity(FinalGradeDto finalGradeDto) {
		FinalGradeEntity finalGradeEntity = new FinalGradeEntity();
		finalGradeEntity.setSemester(finalGradeDto.getSemester());
		finalGradeEntity.setValue(3);
		finalGradeEntity.setPupil(userService.findPupilById(finalGradeDto.getPupilId()));
		finalGradeEntity.setSubject(subjectService.findById(finalGradeDto.getSubjectId()));
		finalGradeRepository.save(finalGradeEntity);
		return finalGradeEntity;
	}

}
