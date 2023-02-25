package com.iktpreobuka.zavrsni.services;

import java.util.NoSuchElementException;

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
		SubjectEntity subjectEntity;
		if(subjectDto.getId() != null) {
			subjectEntity = subjectRepository.findById(subjectDto.getId()).get();
		}else {
			subjectEntity = new SubjectEntity();
		}
		subjectEntity.setClassLoad(subjectDto.getClassLoad());
		subjectEntity.setName(subjectDto.getName());
		subjectEntity.setYear(subjectDto.getYear());
		return subjectRepository.save(subjectEntity);
	}

	@Override
	public SubjectEntity findById(Integer id) {
		SubjectEntity entity;
		try {
			entity = subjectRepository.findById(id).get();
			return entity;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Subject with id: " + id + " does not exist.");
		}
	}

}
