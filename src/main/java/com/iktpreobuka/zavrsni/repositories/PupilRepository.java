package com.iktpreobuka.zavrsni.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.zavrsni.entities.PupilEntity;

public interface PupilRepository extends CrudRepository<PupilEntity, Integer> {

	Optional<PupilEntity> findByEmail(String email);
}
