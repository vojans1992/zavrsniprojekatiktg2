package com.iktpreobuka.zavrsni.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.zavrsni.entities.TeacherSubjectDepartmentEntity;
import com.iktpreobuka.zavrsni.entities.TeacherSubjectDepartmentKey;

public interface TeacherSubjectDepartmentRepository extends CrudRepository<TeacherSubjectDepartmentEntity, TeacherSubjectDepartmentKey>{

	List<TeacherSubjectDepartmentEntity> findByTeacherId(int teacherId);
}
