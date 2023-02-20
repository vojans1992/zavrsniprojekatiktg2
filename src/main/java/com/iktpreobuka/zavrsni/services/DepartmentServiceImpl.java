package com.iktpreobuka.zavrsni.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.DepartmentEntity;
import com.iktpreobuka.zavrsni.entities.Year;
import com.iktpreobuka.zavrsni.entities.dto.DepartmentDto;
import com.iktpreobuka.zavrsni.repositories.DepartmentRepository;
@Service
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentRepository departmentRepostitory;
	@Autowired
	private UserService userService;
	
	@Override
	public DepartmentEntity findById(Integer id) {
		// TODO Auto-generated method stub
		return departmentRepostitory.findById(id).get();
	}

	@Override
	public DepartmentEntity saveDepartmentDtoAsDepartmentEntity(DepartmentDto departmentDto) {
		DepartmentEntity departmentEntity = new DepartmentEntity();
		if(departmentDto.getYear() == null) {
			departmentEntity.setYear(Year.FIRST_YEAR);
		}else {
			departmentEntity.setYear(departmentDto.getYear());
		}
		departmentEntity.setHomeroomTeacher(userService.findTeacherById(departmentDto.getHomeroomTeacherId()));
		departmentRepostitory.save(departmentEntity);
		return departmentEntity;
	}

}
