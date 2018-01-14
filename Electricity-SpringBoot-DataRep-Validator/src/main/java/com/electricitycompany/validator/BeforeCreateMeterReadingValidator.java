package com.electricitycompany.validator;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.electricitycompany.model.ConsumptionFraction;
import com.electricitycompany.model.MeterReading;
import com.electricitycompany.model.Profile;
import com.electricitycompany.repository.ConsumptionFractionRepository;
import com.electricitycompany.repository.ProfileRepository;

public class BeforeCreateMeterReadingValidator implements Validator {

	public static final Double FRACTION_TOLERANCE_RATIO = 0.25;
	private ProfileRepository profileRepository;
	private ConsumptionFractionRepository fractionRepository;

	public BeforeCreateMeterReadingValidator(ProfileRepository profileRepository,
			ConsumptionFractionRepository fractionRepository) {
		this.profileRepository = profileRepository;
		this.fractionRepository = fractionRepository;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	@Override
	public void validate(Object reads, Errors errors) {
		@SuppressWarnings("unchecked")
		List<MeterReading> readings = (List<MeterReading>) reads;
		Map<Long, List<MeterReading>> meterReadings = readings.stream()
				.collect(Collectors.groupingBy(MeterReading::getMeterId));

		meterReadings.forEach((meterId, flist) -> {
			Profile profile = profileRepository.findByMeterId(meterId);
			if (profile == null) {
				errors.reject("meterreading.profile.notFound", new Object[] { meterId }, "");
				flist.forEach(mt -> mt.setValidated(false));
				return;
			}

			Collections.sort(flist, (p1, p2) -> Long.compare(p1.getMonth(), p2.getMonth()));

			long totalConsumption = flist.get(flist.size() - 1).getAmount();
			long previousAmount = 0L;
			for (int i = 0; i < flist.size(); i++) {
				MeterReading current = flist.get(i);
				if (current.getAmount() < previousAmount) {
					errors.reject("meterreading.amount.lessthan.previous",
							new Object[] { current.getAmount(), current.getMonth(), previousAmount }, "");
					current.setValidated(false);
				}
				
				long currentConsumption = current.getAmount() - previousAmount;
				ConsumptionFraction fraction = fractionRepository.findByProfileIdAndMonth(profile.getId(),
						current.getMonth());
				if (fraction != null) {
					long minValue = Math.round(totalConsumption * fraction.getValue() * (1 - FRACTION_TOLERANCE_RATIO));
					long maxValue = Math.round(totalConsumption * fraction.getValue() * (1 + FRACTION_TOLERANCE_RATIO));
					if (currentConsumption < minValue || currentConsumption > maxValue) {
						errors.reject("meterreading.consumption.not.range",
								new Object[] { current.getMeterId(), current.getMonth() }, "");
						current.setValidated(false);
					}
				}
				previousAmount = current.getAmount();
			}

		});

	}

}
