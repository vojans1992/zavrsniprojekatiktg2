package com.iktpreobuka.zavrsni.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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
			try {
				subjectEntity = subjectRepository.findById(subjectDto.getId()).get();
			} catch (NoSuchElementException e) {
				throw new NoSuchElementException("Subject with ID: " + subjectDto.getId() + " does not exist.");
			}
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
	
	@Override
	public String deleteById(Integer id) {
		try {
			subjectRepository.deleteById(id);
			return "Deleted subject with ID: " + id;
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultDataAccessException("Subject with ID: " + id + " does not exist.", 1);
		}
	}

}
