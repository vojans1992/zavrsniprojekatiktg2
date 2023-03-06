package com.iktpreobuka.zavrsni.services;


import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.entities.dto.GradeDto;

public interface GradeService {

	GradeEntity findById(Integer id);
	GradeEntity saveGradeDtoAsGradeEntity(GradeDto gradeDto);
	String deleteById(int id);
}
