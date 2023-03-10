package com.iktpreobuka.zavrsni.services;

import java.util.List;

import com.iktpreobuka.zavrsni.entities.FinalGradeEntity;
import com.iktpreobuka.zavrsni.entities.GradeEntity;

public interface ParentService {

	List<GradeEntity> getChildGrades(String parentEmail, String childEmail);
	List<GradeEntity> getChildGradesBySubject(String parentEmail, String childEmail, String subjectName);
	List<FinalGradeEntity> getChildFinalGrades(String name, String childEmail);
}
