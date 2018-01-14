package com.electricitycompany.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electricitycompany.model.MeterReading;
import com.electricitycompany.repository.MeterReadingRepository;

@Service
public class MeterReadingService {

	@Autowired
	private MeterReadingRepository meterReadingRepository;

	public List<MeterReading> getAll() {
		return StreamSupport.stream(meterReadingRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	public MeterReading getOne(Long id) {
		return meterReadingRepository.findOne(id);
	}

	public MeterReadingRepository getMeterReadingRepository() {
		return meterReadingRepository;
	}

	public void delete(Long id) {
		meterReadingRepository.delete(id);
	}

	public MeterReading save(MeterReading meterReading) {
		return meterReadingRepository.save(meterReading);
	}

	public MeterReading getByMeterIdAndMonth(Long meterId, short month){
		return meterReadingRepository.findByMeterIdAndMonth(meterId, month);
	}
}
