package com.electricitycompany.rest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.electricitycompany.model.Profile;
import com.electricitycompany.repository.ConsumptionFractionRepository;
import com.electricitycompany.repository.ProfileRepository;
import com.electricitycompany.service.ProfileService;

import net.minidev.json.JSONArray;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class MeterProfileControllerTest extends BaseRestTest {

	@MockBean
	private ProfileService profileService;

	@MockBean
	ProfileRepository profileRepository;

	@MockBean
	ConsumptionFractionRepository consumptionFractionRepository;

	private List<Profile> profiles;

	@Before
	public void setup() throws Exception {
		super.setup();

		profiles = new ArrayList<>();

		Profile profile = new Profile();
		profile.setId(1L);
		profile.setMeterId(1L);
		profile.setName("John");
		profile.setSurname("Smith");
		profiles.add(profile);

		Profile profile2 = new Profile();
		profile2.setId(2L);
		profile2.setMeterId(1L);
		profile2.setName("Ahmet");
		profile2.setSurname("Tan");
		profiles.add(profile2);
	}

	@Test
	public void getAllProfiles() throws Exception {

		when(profileService.getAll()).thenReturn(profiles);

		mockMvc.perform(get("/profiles/")).andExpect(status().isOk()).andExpect(jsonPath("$", isA(JSONArray.class)))
				.andExpect(jsonPath("$.length()", is(2))).andReturn();
	}

	@Test
	public void getProfile() throws Exception {

		Profile p = profiles.get(0);
		when(profileService.getOne((long) 1)).thenReturn(p);

		mockMvc.perform(get("/profiles/{id}", p.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(p.getId().intValue())))
				.andExpect(jsonPath("$.meterId", is(p.getMeterId().intValue())))
				.andExpect(jsonPath("$.name", is(p.getName()))).andExpect(jsonPath("$.surname", is(p.getSurname())));
	}

	@Test
	public void addProfile() throws Exception {
		Profile pf = profiles.get(0);
		pf.setName("Test");
		;

		when(profileService.save(pf)).thenReturn(pf);

		String content = json(pf);

		mockMvc.perform(put("/profiles/add").accept(JSON_MEDIA_TYPE).content(content).contentType(JSON_MEDIA_TYPE))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteMeterReading() throws Exception {

		Profile pf = profiles.get(0);

		mockMvc.perform(delete("/profiles/{id}", pf.getId())).andExpect(status().isOk()).andReturn();
	}
}
