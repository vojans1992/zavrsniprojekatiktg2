package com.iktpreobuka.zavrsni.services;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.zavrsni.entities.RoleEntity;
import com.iktpreobuka.zavrsni.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public RoleEntity findById(Integer id) {
		RoleEntity entity;
		try {
			entity = roleRepository.findById(id).get();
			return entity;
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("Role with id: " + id + " does not exist.");
		}
	}

	
	
}
