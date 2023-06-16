package com.iktpreobuka.zavrsni.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.auditing.IsNewAwareAuditingHandler;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.DepartmentEntity;
import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.SubjectEntity;
import com.iktpreobuka.zavrsni.entities.TeacherEntity;
import com.iktpreobuka.zavrsni.entities.TeacherSubjectDepartmentEntity;

@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private UserService userService;
	@Autowired
	private TeacherSubjectDepartmentService teacherSubjectDepartmentService;

	@Override
	public List<GradeEntity> getAllGrades(String teacherEmail) {
		TeacherEntity teacher = (TeacherEntity) userService.findUserByEmail(teacherEmail);
		List<GradeEntity> list = new ArrayList<>();
		for (GradeEntity gradeEntity : teacher.getGrades()) {
			list.add(gradeEntity);
		}
		return list;
	}

	@Override
	public List<GradeEntity> getAllGradesBySubject(String teacherEmail, String subjectName) {
		TeacherEntity teacher = (TeacherEntity) userService.findUserByEmail(teacherEmail);
		List<GradeEntity> grades = new ArrayList<>();
		for (GradeEntity gradeEntity : teacher.getGrades()) {
			if (gradeEntity.getSubject().getName().equals(subjectName)) {
				grades.add(gradeEntity);
			}
		}
		return grades;
	}

	@Override
	public List<GradeEntity> getAllGradesByPupil(String teacherEmail, String pupilEmail) {
		TeacherEntity teacher = (TeacherEntity) userService.findUserByEmail(teacherEmail);
		List<GradeEntity> grades = new ArrayList<>();
		for (GradeEntity gradeEntity : teacher.getGrades()) {
			if (gradeEntity.getPupil().getEmail().equals(pupilEmail)) {
				grades.add(gradeEntity);
			}
		}
		return grades;
	}

	@Override
	public List<GradeEntity> getAllGradesBySubjectAndPupil(String teacherEmail, String subjectName, String pupilEmail) {
		TeacherEntity teacher = (TeacherEntity) userService.findUserByEmail(teacherEmail);
		List<GradeEntity> grades = new ArrayList<>();
		for (GradeEntity gradeEntity : teacher.getGrades()) {
			if (gradeEntity.getSubject().getName().equals(subjectName)
					&& gradeEntity.getPupil().getEmail().equals(pupilEmail)) {
				grades.add(gradeEntity);
			}
		}
		return grades;
	}

	@Override
	public List<SubjectEntity> getSubjects(String email) {
		TeacherEntity teacher = (TeacherEntity) userService.findUserByEmail(email);
		List<TeacherSubjectDepartmentEntity> listTSD = teacherSubjectDepartmentService
				.getAllForTeacher(teacher.getId());
		List<SubjectEntity> listSubjects = new ArrayList<>();
		for (TeacherSubjectDepartmentEntity tsd : listTSD) {
			listSubjects.add(tsd.getSubject());
		}
		return listSubjects;

	}

	@Override
	public List<DepartmentEntity> getDepartments(String email) {
		TeacherEntity teacher = (TeacherEntity) userService.findUserByEmail(email);
		List<TeacherSubjectDepartmentEntity> listTSD = teacherSubjectDepartmentService
				.getAllForTeacher(teacher.getId());
		List<DepartmentEntity> listDepartments = new ArrayList<>();
		for (TeacherSubjectDepartmentEntity tsd : listTSD) {
			if (!listDepartments.contains(tsd.getDepartment())) {
				listDepartments.add(tsd.getDepartment());
			}
		}
		return listDepartments;

	}

	@Override
	public List<PupilEntity> getStudentsByDepartment(String email, int departmentId) {
		List<DepartmentEntity> listDepartments = getDepartments(email);
		List<PupilEntity> listPupils = new ArrayList<>();
		for (DepartmentEntity departmentEntity : listDepartments) {
			if (departmentEntity.getId() == departmentId) {
				listPupils.addAll(departmentEntity.getPupils());
			}
		}

		return listPupils;
	}

	@Override
	public List<PupilEntity> getPupils(String email) {
		List<PupilEntity> pupils = new ArrayList<>();
		List<DepartmentEntity> departments = getDepartments(email);
		for (DepartmentEntity departmentEntity : departments) {
			for (PupilEntity pupil : departmentEntity.getPupils()) {
				pupils.add(pupil);
			}
		}
		return pupils;
	}

	@Override
	public HashMap<Integer, List<SubjectEntity>> getDepartmentsAndSubjects(String email) {
		TeacherEntity teacher = (TeacherEntity) userService.findUserByEmail(email);
		HashMap<Integer, List<SubjectEntity>> data = new HashMap<>();
		List<TeacherSubjectDepartmentEntity> listTSD = teacherSubjectDepartmentService
				.getAllForTeacher(teacher.getId());
		DepartmentEntity departmentEntity = new DepartmentEntity();
		for (TeacherSubjectDepartmentEntity tsd : listTSD) {
			if(!data.containsKey(tsd.getDepartment().getId())) {
				departmentEntity = tsd.getDepartment();
				data.put(departmentEntity.getId(), new ArrayList<SubjectEntity>());
				data.get(departmentEntity.getId()).add(tsd.getSubject());
			}else {
				data.get(departmentEntity.getId()).add(tsd.getSubject());
			}
		}
		return data;
	}
}
