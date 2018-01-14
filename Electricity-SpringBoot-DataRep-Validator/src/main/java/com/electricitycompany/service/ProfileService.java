package com.electricitycompany.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electricitycompany.model.Profile;
import com.electricitycompany.repository.ProfileRepository;

@Service
public class ProfileService {

	@Autowired
	private ProfileRepository profileRepository;

	public List<Profile> getAll() {
		return StreamSupport.stream(profileRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	public Profile getOne(Long id) {
		return profileRepository.findOne(id);
	}

	public ProfileRepository getProfileRepository() {
		return profileRepository;
	}

	public void delete(Long id) {
		profileRepository.delete(id);
	}

	public Profile save(Profile profile) {
		return profileRepository.save(profile);
	}

}
