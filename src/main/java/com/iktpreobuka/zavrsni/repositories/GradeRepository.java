package com.iktpreobuka.zavrsni.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.zavrsni.entities.GradeEntity;


public interface GradeRepository extends CrudRepository<GradeEntity, Integer>{

	List<GradeEntity> findByPupilId(int pupilId);
}
