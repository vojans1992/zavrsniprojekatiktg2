package com.iktpreobuka.zavrsni.services;

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
		// TODO Auto-generated method stub
		return roleRepository.findById(id).get();
	}

	
	
}
