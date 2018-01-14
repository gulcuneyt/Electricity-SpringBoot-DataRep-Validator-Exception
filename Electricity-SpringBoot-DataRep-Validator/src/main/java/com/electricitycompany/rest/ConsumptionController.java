package com.electricitycompany.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.electricitycompany.model.MeterReading;
import com.electricitycompany.service.MeterReadingService;

@RestController
@RequestMapping("/consumption")
public class ConsumptionController {

	@Autowired
	private MeterReadingService meterReadingService;

	@RequestMapping(value = "/retrieve", method = RequestMethod.GET)
	public ResponseEntity<Object> retrieve(@RequestParam("month") Short month, @RequestParam("meterId") Long meterId) {

		MeterReading monthReading = meterReadingService.getByMeterIdAndMonth(meterId, month);
		if (monthReading == null) {
			return ResponseEntity.notFound().build();
		}
		MeterReading previousReading = meterReadingService.getByMeterIdAndMonth(meterId, (short) (month - 1));
		if (previousReading == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(monthReading.getAmount() - previousReading.getAmount());
	}
}
