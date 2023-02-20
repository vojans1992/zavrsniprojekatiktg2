package com.iktpreobuka.zavrsni.services;

import com.iktpreobuka.zavrsni.entities.SubjectEntity;
import com.iktpreobuka.zavrsni.entities.dto.SubjectDto;

public interface SubjectService {

	SubjectEntity findById(Integer id);
	SubjectEntity saveSubjectDtoAsSubjectEntity(SubjectDto subjectDto);
	
}
