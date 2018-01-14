package com.electricitycompany.rest;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import com.electricitycompany.model.MeterReading;
import com.electricitycompany.model.Profile;
import com.electricitycompany.repository.ConsumptionFractionRepository;
import com.electricitycompany.repository.ProfileRepository;
import com.electricitycompany.service.MeterReadingService;

import net.minidev.json.JSONArray;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class MeterReadingControllerTest extends BaseRestTest {

	@MockBean
	private MeterReadingService meterReadingService;

	@MockBean
	ProfileRepository profileRepository;

	@MockBean
	ConsumptionFractionRepository consumptionFractionRepository;

	private List<MeterReading> meterReadings;

	@Before
	public void setup() throws Exception {
		super.setup();

		meterReadings = new ArrayList<>();
		for (int i = 1; i <= 12; i++) {
			MeterReading meterReading = new MeterReading();
			meterReading.setId((long) i);
			meterReading.setMeterId(1L);
			meterReading.setMonth((short) i);
			meterReading.setAmount((i * 10L));
			meterReadings.add(meterReading);
		}
	}

	@Test
	public void getAllMeterReadings() throws Exception {

		when(meterReadingService.getAll()).thenReturn(meterReadings);

		mockMvc.perform(get("/meterreadings/")).andExpect(status().isOk())
				.andExpect(jsonPath("$", isA(JSONArray.class))).andExpect(jsonPath("$.length()", is(12))).andReturn();
	}

	@Test
	public void getMeterReading() throws Exception {

		MeterReading md = meterReadings.get(0);
		when(meterReadingService.getOne((long) 1)).thenReturn(md);

		mockMvc.perform(get("/meterreadings/{id}", md.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(md.getId().intValue())))
				.andExpect(jsonPath("$.meterId", is(md.getMeterId().intValue())))
				.andExpect(jsonPath("$.month", is(md.getMonth().intValue())))
				.andExpect(jsonPath("$.amount", is(md.getAmount().intValue()))).andReturn();
	}

	@Test
	public void addAll_ValidationError_ProfileNotFound() throws Exception {
		String content = json(meterReadings);
		mockMvc.perform(
				post("/meterreadings/addAll").accept(JSON_MEDIA_TYPE).content(content).contentType(JSON_MEDIA_TYPE))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$..*", hasItem("meterreading.profile.notFound")));
	}

	@Test
	public void addAll_ValidationError_MonthReadingLessThanPreviousMonthReading() throws Exception {

		meterReadings.get(4).setAmount(30L);

		Profile profile = new Profile();
		profile.setId(1L);
		profile.setMeterId(1L);
		profile.setName("Smith");
		profile.setSurname("John");

		when(profileRepository.findByMeterId((long) 1)).thenReturn(profile);

		String content = json(meterReadings);
		System.out.println(content);
		mockMvc.perform(
				post("/meterreadings/addAll").accept(JSON_MEDIA_TYPE).content(content).contentType(JSON_MEDIA_TYPE))
				.andDo(print()).andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$..*", hasItem("meterreading.amount.lessthan.previous")));
	}

	@Test
	public void addAll_ValidationError_ConsumptionNotInRange() throws Exception {
		Profile profile = new Profile();
		profile.setId(1L);
		profile.setMeterId(1L);
		profile.setName("Smith");
		profile.setSurname("John");

		ConsumptionFraction consumptionFraction = new ConsumptionFraction();
		consumptionFraction.setId(1L);
		consumptionFraction.setMonth((short) 3);
		consumptionFraction.setProfileId(1L);
		consumptionFraction.setValue(0.4);

		when(profileRepository.findByMeterId((long) 1)).thenReturn(profile);

		when(consumptionFractionRepository.findByProfileIdAndMonth(1L, (short) 3)).thenReturn(consumptionFraction);

		String content = json(meterReadings);
		System.out.println(content);
		mockMvc.perform(
				post("/meterreadings/addAll").accept(JSON_MEDIA_TYPE).content(content).contentType(JSON_MEDIA_TYPE))
				.andDo(print()).andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$..*", hasItem("meterreading.consumption.not.range")));
	}

	@Test
	public void addMeterReading() throws Exception {
		MeterReading md = meterReadings.get(0);
		md.setAmount(100L);

		when(meterReadingService.save(md)).thenReturn(md);

		String content = json(md);

		mockMvc.perform(put("/meterreadings/add").accept(JSON_MEDIA_TYPE).content(content).contentType(JSON_MEDIA_TYPE))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteMeterReading() throws Exception {

		MeterReading md = meterReadings.get(0);

		mockMvc.perform(delete("/meterreadings/{id}", md.getId())).andExpect(status().isOk()).andReturn();
	}

	@Test
	public void addAll() throws Exception {
		// TODO: write addAll unit test
	}

}
