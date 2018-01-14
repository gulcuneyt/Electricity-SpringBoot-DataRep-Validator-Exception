package com.electricitycompany.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

import com.electricitycompany.model.MeterReading;
import com.electricitycompany.service.MeterReadingService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class ConsumptionControllerTest extends BaseRestTest {

	@MockBean
	private MeterReadingService meterReadingService;

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
	public void retrieveConsumption() throws Exception {

		MeterReading month3 = meterReadings.get(2);
		MeterReading month2 = meterReadings.get(1);

		when(meterReadingService.getByMeterIdAndMonth((long) 1, (short) 3)).thenReturn(month3);
		when(meterReadingService.getByMeterIdAndMonth((long) 1, (short) 2)).thenReturn(month2);

		mockMvc.perform(get("/consumption/retrieve").param("month", "3").param("meterId", "1")).andDo(print())
				.andExpect(status().isOk()).andExpect(content().string("10"));
	}
}
