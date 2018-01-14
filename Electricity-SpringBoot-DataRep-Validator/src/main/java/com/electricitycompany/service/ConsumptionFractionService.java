package com.electricitycompany.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.electricitycompany.model.ConsumptionFraction;
import com.electricitycompany.repository.ConsumptionFractionRepository;

@Service
public class ConsumptionFractionService {

	@Autowired
	private ConsumptionFractionRepository fractionRepository;

	public List<ConsumptionFraction> getAll() {
		return StreamSupport.stream(fractionRepository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	public ConsumptionFraction getOne(Long id) {
		return fractionRepository.findOne(id);
	}

	
	public ConsumptionFractionRepository getFractionRepository() {
		return fractionRepository;
	}

	public void delete(Long id) {
		fractionRepository.delete(id);
	}

	public ConsumptionFraction save(ConsumptionFraction fraction) {
		return fractionRepository.save(fraction);
	}
	
}
