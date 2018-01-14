package com.electricitycompany.validator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.electricitycompany.model.ConsumptionFraction;
import com.electricitycompany.repository.ConsumptionFractionRepository;

public class BeforeCreateConsumptionFractionValidator implements Validator {

	private ConsumptionFractionRepository fractionRepository;

	public BeforeCreateConsumptionFractionValidator(ConsumptionFractionRepository repository) {
		fractionRepository = repository;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	public ConsumptionFractionRepository getFractionRepository() {
		return fractionRepository;
	}

	@Override
	public void validate(Object consumptionFractions, Errors errors) {
		@SuppressWarnings("unchecked")
		List<ConsumptionFraction> fractions = (List<ConsumptionFraction>) consumptionFractions;
		Map<Long, List<ConsumptionFraction>> profileFractions = fractions.stream()
				.collect(Collectors.groupingBy(ConsumptionFraction::getProfileId));
		profileFractions.forEach((profile, flist) -> {
			double total = flist.stream().mapToDouble(fr -> fr.getValue()).sum();
			if (total != 1) {
				errors.reject("fraction.amount.total", new Object[] { total, profile }, "");
			}
		});

	}

}
