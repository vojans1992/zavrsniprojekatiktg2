package com.iktpreobuka.zavrsni.services;

import com.iktpreobuka.zavrsni.entities.DepartmentEntity;
import com.iktpreobuka.zavrsni.entities.dto.DepartmentDto;

public interface DepartmentService {

	DepartmentEntity findById(Integer id);
	DepartmentEntity saveDepartmentDtoAsDepartmentEntity(DepartmentDto departmentDto);
}
