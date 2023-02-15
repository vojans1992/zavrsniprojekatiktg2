package com.iktpreobuka.zavrsni.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.zavrsni.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

}
