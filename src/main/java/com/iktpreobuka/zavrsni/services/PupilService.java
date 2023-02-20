package com.iktpreobuka.zavrsni.services;

import com.iktpreobuka.zavrsni.entities.PupilEntity;
import com.iktpreobuka.zavrsni.entities.dto.PupilDto;

public interface PupilService {

	PupilEntity findById(Integer id);
	PupilEntity savePupilDtoAsPupilEntity(PupilDto pupilDto);
}
