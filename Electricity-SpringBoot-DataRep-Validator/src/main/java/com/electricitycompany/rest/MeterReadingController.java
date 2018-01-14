package com.electricitycompany.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.electricitycompany.exception.ServiceException;
import com.electricitycompany.model.MeterReading;
import com.electricitycompany.repository.ConsumptionFractionRepository;
import com.electricitycompany.repository.ProfileRepository;
import com.electricitycompany.service.MeterReadingService;
import com.electricitycompany.validator.BeforeCreateMeterReadingValidator;

@RestController
@RequestMapping(value = "/meterreadings", produces = MediaType.APPLICATION_JSON_VALUE)
public class MeterReadingController {

	@Autowired
	private MeterReadingService meterReadingService;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ConsumptionFractionRepository fractionRepository;

	@RequestMapping(value = "/addAll", method = RequestMethod.POST)
	public ResponseEntity<List<MeterReading>> addAll(@Valid @RequestBody List<MeterReading> meterReadings,
			Errors errors) throws ServiceException {
		meterReadings.forEach(mt -> {
			if (mt.isValidated()) {
				meterReadingService.save(mt);
			}
		});
		if (errors.hasErrors()) {
			throw new ServiceException(errors);
		}
		return new ResponseEntity<List<MeterReading>>(meterReadings, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public List<MeterReading> getAll() {
		return meterReadingService.getAll();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<MeterReading> get(@PathVariable("id") Long id) {
		MeterReading meterReading = meterReadingService.getOne(id);
		return (meterReading == null ? ResponseEntity.status(HttpStatus.NOT_FOUND) : ResponseEntity.ok())
				.body(meterReading);

	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		meterReadingService.delete(id);
	}

	@RequestMapping(path = "/add", method = RequestMethod.PUT)
	public ResponseEntity<MeterReading> add(@RequestBody MeterReading meterReading) {
		MeterReading mt = meterReadingService.save(meterReading);
		return ResponseEntity.ok().body(mt);
	}

	@InitBinder()
	public void setupBinder(WebDataBinder binder) {
		binder.addValidators(new BeforeCreateMeterReadingValidator(profileRepository, fractionRepository));
	}

}
