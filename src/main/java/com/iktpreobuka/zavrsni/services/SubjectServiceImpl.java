package com.iktpreobuka.zavrsni.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.SubjectEntity;
import com.iktpreobuka.zavrsni.entities.dto.SubjectDto;
import com.iktpreobuka.zavrsni.repositories.SubjectRepository;
@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;
	
	@Override
	public SubjectEntity saveSubjectDtoAsSubjectEntity(SubjectDto subjectDto) {
		SubjectEntity subjectEntity = new SubjectEntity();
		subjectEntity.setClassLoad(subjectDto.getClassLoad());
		subjectEntity.setName(subjectDto.getName());
		subjectEntity.setYear(subjectDto.getYear());
		subjectRepository.save(subjectEntity);
		return subjectEntity;
	}

	@Override
	public SubjectEntity findById(Integer id) {
		// TODO Auto-generated method stub
		return subjectRepository.findById(id).get();
	}

}
