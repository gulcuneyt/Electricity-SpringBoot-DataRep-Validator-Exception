package com.electricitycompany.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.electricitycompany.model.Profile;
import com.electricitycompany.service.ProfileService;

@RestController
@RequestMapping("/profiles")
public class MeterProfileController {

	@Autowired
	private ProfileService profileService;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public List<Profile> getAll() {
		return profileService.getAll();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Profile> get(@PathVariable("id") Long id) {
		Profile profile = profileService.getOne(id);
		return (profile == null ? ResponseEntity.status(HttpStatus.NOT_FOUND) : ResponseEntity.ok())
				.body(profile);
		
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		profileService.delete(id);
	}

	@RequestMapping(path = "/add", method = RequestMethod.PUT)
	public ResponseEntity<Profile> add(@RequestBody Profile profile) {
		Profile pf = profileService.save(profile);
		return ResponseEntity.ok().body(pf);
	}

}
