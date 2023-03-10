package com.iktpreobuka.zavrsni.services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.DepartmentEntity;
import com.iktpreobuka.zavrsni.entities.SubjectEntity;
import com.iktpreobuka.zavrsni.entities.TeacherEntity;
import com.iktpreobuka.zavrsni.entities.TeacherSubjectDepartmentEntity;
import com.iktpreobuka.zavrsni.entities.TeacherSubjectDepartmentKey;
import com.iktpreobuka.zavrsni.repositories.TeacherSubjectDepartmentRepository;
import com.iktpreobuka.zavrsni.utils.CustomDataBindingException;

@Service
public class TeacherSubjectDepartmentServiceImpl implements TeacherSubjectDepartmentService {

	@Autowired
	private TeacherSubjectDepartmentRepository teacherSubjectDepartmentRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private DepartmentService departmentService;

	@Override
	public TeacherSubjectDepartmentEntity save(int teacherId, int subjectId, int departmentId) {
		TeacherEntity teacher;
		try {
			teacher = (TeacherEntity) userService.findUserById(teacherId);
		} catch (ClassCastException e) {
			throw new ClassCastException("User with ID: " + teacherId + " is not a teacher entity.");
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}
		
		SubjectEntity subject;
		try {
			subject = subjectService.findById(subjectId);
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}
		
		DepartmentEntity department;
		try {
			department = departmentService.findById(departmentId);
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException(e.getMessage());
		}
		
		if(subject.getYear() != department.getYear()) {
			throw new CustomDataBindingException("Department: " + department.getName() + " can not study subject: " + 
					subject.getName() + " because they are not part of the same year.");
		}
		
		TeacherSubjectDepartmentEntity tSD = new TeacherSubjectDepartmentEntity(teacher, subject, department);
		teacherSubjectDepartmentRepository.save(tSD);
		return tSD;
	}

	@Override
	public TeacherSubjectDepartmentEntity findById(int teacherId, int subjectId, int departmentId) {
		TeacherSubjectDepartmentEntity entity;
		TeacherSubjectDepartmentKey key = new TeacherSubjectDepartmentKey(teacherId, subjectId, departmentId);
		try {
			entity = teacherSubjectDepartmentRepository.findById(key).get();
			return entity;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("TSD with id: " + key + " does not exist.");
		}
		
	}
	
	@Override
	public TeacherSubjectDepartmentEntity delete(int teacherId, int subjectId, int departmentId) {
		TeacherSubjectDepartmentEntity entity = findById(teacherId, subjectId, departmentId);
		teacherSubjectDepartmentRepository.delete(entity);
		return entity;
	}
	
	@Override
	public List<TeacherSubjectDepartmentEntity> getAllForTeacher(int teacherId){
		return teacherSubjectDepartmentRepository.findByTeacherId(teacherId);
	}
}
