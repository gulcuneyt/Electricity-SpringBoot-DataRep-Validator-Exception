package com.electricitycompany.rest;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.electricitycompany.model.ConsumptionFraction;
import com.electricitycompany.repository.ConsumptionFractionRepository;
import com.electricitycompany.repository.ProfileRepository;
import com.electricitycompany.service.ConsumptionFractionService;

import net.minidev.json.JSONArray;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class ConsumptionFractionControllerTest extends BaseRestTest {

	@MockBean
	private ConsumptionFractionService consumptionFractionService;

	@MockBean
	ProfileRepository profileRepository;

	@MockBean
	ConsumptionFractionRepository consumptionFractionRepository;

	private List<ConsumptionFraction> consumptionFractions;

	@Before
	public void setup() throws Exception {
		super.setup();

		consumptionFractions = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			ConsumptionFraction consumptionFraction = new ConsumptionFraction();
			consumptionFraction.setId((long) i);
			consumptionFraction.setProfileId(1L);
			;
			consumptionFraction.setMonth((short) i);
			consumptionFraction.setValue(0.5);
			consumptionFractions.add(consumptionFraction);
		}
	}

	@Test
	public void getAllConsumptionFractions() throws Exception {

		when(consumptionFractionService.getAll()).thenReturn(consumptionFractions);

		mockMvc.perform(get("/consumptionfractions/")).andExpect(status().isOk())
				.andExpect(jsonPath("$", isA(JSONArray.class))).andExpect(jsonPath("$.length()", is(12))).andReturn();
	}

	@Test
	public void getConsumptionFraction() throws Exception {

		ConsumptionFraction cf = consumptionFractions.get(0);
		when(consumptionFractionService.getOne((long) 1)).thenReturn(cf);

		mockMvc.perform(get("/consumptionfractions/{id}", cf.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(cf.getId().intValue())))
				.andExpect(jsonPath("$.profileId", is(cf.getProfileId().intValue())))
				.andExpect(jsonPath("$.month", is(cf.getMonth().intValue())))
				.andExpect(jsonPath("$.value", is(cf.getValue()))).andReturn();
	}

	@Test
	public void addAll_ValidationError_TotalFractionValuesShouldBeOne() throws Exception {
		String content = json(consumptionFractions);
		mockMvc.perform(post("/consumptionfractions/addAll").accept(JSON_MEDIA_TYPE).content(content)
				.contentType(JSON_MEDIA_TYPE)).andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$..*", hasItem("fraction.amount.total")));
	}

	@Test
	public void addConsumptionFraction() throws Exception {
		ConsumptionFraction cf = consumptionFractions.get(0);
		cf.setValue(0.6);

		when(consumptionFractionService.save(cf)).thenReturn(cf);

		String content = json(cf);

		mockMvc.perform(
				put("/consumptionfractions/add").accept(JSON_MEDIA_TYPE).content(content).contentType(JSON_MEDIA_TYPE))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteConsumptionFraction() throws Exception {

		ConsumptionFraction cf = consumptionFractions.get(0);

		mockMvc.perform(delete("/consumptionfractions/{id}", cf.getId())).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void addAll() throws Exception {
		// TODO: write addAll unit test
	}

}
