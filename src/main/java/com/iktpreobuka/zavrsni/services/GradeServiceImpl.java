package com.iktpreobuka.zavrsni.services;

import java.util.Date;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.SubjectEntity;
import com.iktpreobuka.zavrsni.entities.TeacherEntity;
import com.iktpreobuka.zavrsni.entities.TeacherSubjectDepartmentEntity;
import com.iktpreobuka.zavrsni.entities.dto.EmailDto;
import com.iktpreobuka.zavrsni.entities.dto.GradeDto;
import com.iktpreobuka.zavrsni.repositories.GradeRepository;
import com.iktpreobuka.zavrsni.utils.CustomDataBindingException;
import com.iktpreobuka.zavrsni.utils.CustomException;

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
	@Autowired
	private TeacherSubjectDepartmentService teacherSubjectDepartmentService;
	@Autowired
	private EmailService emailService;
	
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
	public GradeEntity saveGradeDtoAsGradeEntity(GradeDto gradeDto, String teacherEmail) {
		GradeEntity gradeEntity;
		if(gradeDto.getId() != null) {
			try {
				gradeEntity = gradeRepository.findById(gradeDto.getId()).get();
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException("Grade with ID: " + gradeDto.getId() + " does not exist.");
			}
		}else {
			gradeEntity = new GradeEntity();
		}
		
		gradeEntity.setSemester(gradeDto.getSemester());
		gradeEntity.setValue(gradeDto.getValue());
		gradeEntity.setSegment(gradeDto.getSegment());
		gradeEntity.setDate(new Date());
		
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
			teacher = (TeacherEntity) userService.findUserByEmail(teacherEmail);
			gradeEntity.setTeacher(teacher);
		} catch (ClassCastException e) {
			throw new ClassCastException("User with email: " + teacherEmail + " is not a teacher entity.");
		}catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}
		
		TeacherSubjectDepartmentEntity tsd;
		try {
			tsd = teacherSubjectDepartmentService.findById(teacher.getId(), subject.getId(), pupil.getDepartment().getId());
		} catch (Exception e) {
			throw new CustomDataBindingException("Teacher: " + teacher.getLastName() + " must be teaching pupil: "
					+ pupil.getLastName() + " in subject: " + subject.getName() + subject.getYear() + " in order to give them a grade.");
		}
		
		String msg = "Dear " + pupil.getParent().getLastName() + " your child " + pupil.getName() 
		+ " has recieved a " + gradeEntity.getValue() + " for " + gradeEntity.getSegment() + " from " + subject.getName();
		emailService.sendSimpleMessage(new EmailDto(pupil.getParent().getEmail(), "Grade announcement", msg));
		
		return gradeRepository.save(gradeEntity);
	}

	@Override
	public String deleteById(int id, String teacherEmail) {
		GradeEntity grade = findById(id);
		TeacherEntity teacher = (TeacherEntity) userService.findUserByEmail(teacherEmail);
		if(grade.getTeacher() != teacher) {
			throw new CustomException(teacherEmail + " is not allowed to delete grade " + grade.getId() + " since they are not the one that created it.");
		}
		try {
			gradeRepository.deleteById(id);
			return "Deleted grade with ID: " + id;
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("Grade with ID: " + id + " does not exist.", 1);
		}
	}
	
}
