package com.electricitycompany.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.electricitycompany.model.ConsumptionFraction;

public interface ConsumptionFractionRepository extends CrudRepository<ConsumptionFraction, Long> {

	List<ConsumptionFraction> findByProfileId(Long id);

	
	ConsumptionFraction findByProfileIdAndMonth(Long id, Short month);
}
