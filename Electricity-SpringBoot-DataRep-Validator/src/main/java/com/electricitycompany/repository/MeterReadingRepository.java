package com.electricitycompany.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.electricitycompany.model.MeterReading;


public interface MeterReadingRepository extends CrudRepository<MeterReading, Long> {
	List<MeterReading> findByMeterId(Long id);

	MeterReading findByMeterIdAndMonth(Long meterId, short month);
}