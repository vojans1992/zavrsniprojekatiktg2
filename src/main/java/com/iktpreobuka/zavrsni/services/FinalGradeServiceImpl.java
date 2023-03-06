package com.iktpreobuka.zavrsni.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.FinalGradeEntity;
import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.Semester;
import com.iktpreobuka.zavrsni.entities.SubjectEntity;
import com.iktpreobuka.zavrsni.entities.dto.FinalGradeDto;
import com.iktpreobuka.zavrsni.repositories.FinalGradeRepository;
import com.iktpreobuka.zavrsni.utils.CustomPupilEvaluationException;

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
		Semester semester = finalGradeDto.getSemester();
		FinalGradeEntity finalGradeEntity = new FinalGradeEntity();
		
		PupilEntity pupil = pupilService.findById(finalGradeDto.getPupilId());
		SubjectEntity subject = subjectService.findById(finalGradeDto.getSubjectId());
		
		finalGradeEntity.setSemester(finalGradeDto.getSemester());
		
		Double value = 0.0;
		Double count = 0.0;
		for (GradeEntity grade : pupil.getGrades()) {
			if(grade.getSubject().getId() == subject.getId() && grade.getSemester() == semester) {
				value += grade.getValue();
				count++;
			}
		}
		if(count < 3) {
			throw new CustomPupilEvaluationException("Can not give final grade to pupil: " + pupil.getLastName() +
					" because they do not have at least 3 grades from subject: " + subject.getName() + " in semester: " + 
					semester);
		}
		value = value / count ;
		int valueFinal = (int) Math.round(value);
		
		finalGradeEntity.setValue(valueFinal);
		finalGradeEntity.setPupil(pupil);
		finalGradeEntity.setSubject(subject);
		
		finalGradeRepository.save(finalGradeEntity);
		
		return "Pupil: " + pupil.getLastName() + " evaluated to " + valueFinal + " as his final grade from subject: " + subject.getName()
			+ " in semester: " + semester;
	}

}
