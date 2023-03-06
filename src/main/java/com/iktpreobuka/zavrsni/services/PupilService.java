package com.iktpreobuka.zavrsni.services;

import java.util.List;

import com.iktpreobuka.zavrsni.entities.GradeEntity;
import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.dto.PupilDto;

public interface PupilService {

	PupilEntity findById(Integer id);
	PupilEntity savePupilDtoAsPupilEntity(PupilDto pupilDto);
	PupilEntity findByEmail(String email);
	List<GradeEntity> findGradesBySubject(String email, String subjectName);
}
