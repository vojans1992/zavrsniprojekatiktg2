package com.iktpreobuka.zavrsni.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.FinalGradeEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.SubjectEntity;
import com.iktpreobuka.zavrsni.entities.dto.FinalGradeDto;
import com.iktpreobuka.zavrsni.repositories.FinalGradeRepository;

@Service
public class FinalGradeServiceImpl implements FinalGradeService {

	@Autowired
	private FinalGradeRepository finalGradeRepository;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private PupilService pupilService;
	
	@Override
	public FinalGradeEntity findById(Integer id) {
		Optional<FinalGradeEntity> entity = finalGradeRepository.findById(id);
		if(entity.isEmpty())
			return null;
		return entity.get();
	}

	@Override
	public String saveFinalGradeDtoAsFinalGradeEntity(FinalGradeDto finalGradeDto) {
		FinalGradeEntity finalGradeEntity = new FinalGradeEntity();
		PupilEntity pupil = pupilService.findById(finalGradeDto.getPupilId());
		if(pupil == null)
			return "Cannot find pupil with id: " + finalGradeDto.getPupilId();
		SubjectEntity subject = subjectService.findById(finalGradeDto.getSubjectId());
		if(subject == null)
			return "Cannot find subject with id: " + finalGradeDto.getSubjectId(); 
		finalGradeEntity.setSemester(finalGradeDto.getSemester());
		finalGradeEntity.setValue(3);
		finalGradeEntity.setPupil(pupil);
		finalGradeEntity.setSubject(subject);
		finalGradeRepository.save(finalGradeEntity);
		return finalGradeEntity.toString();
	}

}
