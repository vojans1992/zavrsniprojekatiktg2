package com.iktpreobuka.zavrsni.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.zavrsni.entities.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

	Optional<UserEntity> findByEmail(String email);
}
