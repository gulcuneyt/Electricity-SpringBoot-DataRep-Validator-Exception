package com.electricitycompany.repository;

import org.springframework.data.repository.CrudRepository;

import com.electricitycompany.model.Profile;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
	Profile findByMeterId(Long id);

}
