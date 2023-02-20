package com.iktpreobuka.zavrsni.services;

import com.iktpreobuka.zavrsni.entities.FinalGradeEntity;
import com.iktpreobuka.zavrsni.entities.dto.FinalGradeDto;

public interface FinalGradeService {

	FinalGradeEntity findById(Integer id);
	FinalGradeEntity saveFinalGradeDtoAsFinalGradeEntity(FinalGradeDto finalGradeDto);
}
