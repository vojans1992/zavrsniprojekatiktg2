package com.iktpreobuka.zavrsni.services;

import java.util.HashMap;
import java.util.List;

import com.iktpreobuka.zavrsni.entities.DepartmentEntity;
import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.SubjectEntity;

public interface TeacherService {

	List<GradeEntity> getAllGrades(String teacherEmail);

	List<GradeEntity> getAllGradesBySubject(String teacherEmail, String subjectName);

	List<GradeEntity> getAllGradesByPupil(String teacherEmail, String pupilEmail);

	List<GradeEntity> getAllGradesBySubjectAndPupil(String teacherEmail, String subjectName, String pupilEmail);

	List<PupilEntity> getStudentsByDepartment(String email, int departmentId);

	List<DepartmentEntity> getDepartments(String email);

	List<SubjectEntity> getSubjects(String email);

	List<PupilEntity> getPupils(String email);

	HashMap<Integer, List<SubjectEntity>> getDepartmentsAndSubjects(String email);

}
