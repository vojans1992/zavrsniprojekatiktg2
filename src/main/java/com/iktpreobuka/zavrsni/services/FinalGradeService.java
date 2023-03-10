package com.iktpreobuka.zavrsni.services;

import com.iktpreobuka.zavrsni.entities.dto.FinalGradeDto;

public interface FinalGradeService {

	String saveFinalGradeDtoAsFinalGradeEntity(FinalGradeDto finalGradeDto, String userEmail);
}
