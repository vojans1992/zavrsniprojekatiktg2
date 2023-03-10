package com.iktpreobuka.zavrsni.services;


import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.FinalGradeEntity;
import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.Semester;
import com.iktpreobuka.zavrsni.entities.SubjectEntity;
import com.iktpreobuka.zavrsni.entities.TeacherEntity;
import com.iktpreobuka.zavrsni.entities.TeacherSubjectDepartmentEntity;
import com.iktpreobuka.zavrsni.entities.UserEntity;
import com.iktpreobuka.zavrsni.entities.dto.FinalGradeDto;
import com.iktpreobuka.zavrsni.repositories.FinalGradeRepository;
import com.iktpreobuka.zavrsni.utils.CustomException;
import com.iktpreobuka.zavrsni.utils.CustomPupilEvaluationException;

@Service
public class FinalGradeServiceImpl implements FinalGradeService {

	@Autowired
	private FinalGradeRepository finalGradeRepository;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private PupilService pupilService;
	@Autowired
	private UserService userService;
	@Autowired
	private TeacherSubjectDepartmentService teacherSubjectDepartmentService;
	
	

	@Override
	public String saveFinalGradeDtoAsFinalGradeEntity(FinalGradeDto finalGradeDto, String userEmail) {
		UserEntity user = userService.findUserByEmail(userEmail);
		TeacherEntity teacher = null;
		if(user.getRole().getName().equals("ROLE_TEACHER")) {
			teacher = (TeacherEntity) userService.findUserById(user.getId());
		} else {
			throw new CustomException("Only teachers can evaluate pupils. " + userEmail + " is not a teacher.");
		}
		
		Semester semester = finalGradeDto.getSemester();
		FinalGradeEntity finalGradeEntity = new FinalGradeEntity();
		
		PupilEntity pupil = pupilService.findById(finalGradeDto.getPupilId());
		SubjectEntity subject = subjectService.findById(finalGradeDto.getSubjectId());
		TeacherSubjectDepartmentEntity tsd;
		try {
			tsd = teacherSubjectDepartmentService.findById(user.getId(), subject.getId(), pupil.getDepartment().getId());
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Teacher " + user.getLastName() + " is not teaching " + subject.getName() 
			+ " to department: " + pupil.getDepartment().getName() + " and thus is unauthorized to evaluate pupil: " 
			+ pupil.getLastName());
		}
		if(teacher != null && !teacher.getTeacherSubjectDepartments().contains(tsd)) {
			throw new CustomException("Teacher must be teaching subject to pupil in order to give final evaluation");
		}
		
		
		finalGradeEntity.setSemester(semester);
		
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
