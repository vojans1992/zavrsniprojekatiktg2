package com.iktpreobuka.zavrsni.services;

import com.iktpreobuka.zavrsni.entities.TeacherSubjectDepartmentEntity;

public interface TeacherSubjectDepartmentService {

	TeacherSubjectDepartmentEntity save(int teacherId, int subjectId, int departmentId);
	TeacherSubjectDepartmentEntity findById(int teacherId, int subjectId, int departmentId);
	TeacherSubjectDepartmentEntity delete(int teacherId, int subjectId, int departmentId);
}
