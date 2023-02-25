package com.iktpreobuka.zavrsni.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.zavrsni.entities.FinalGradeEntity;

public interface FinalGradeRepository extends CrudRepository<FinalGradeEntity, Integer>{

	FinalGradeEntity findByPupilIdAndSubjectId(int pupilId, int subjectId);
}
