package com.electricitycompany.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.electricitycompany.model.ConsumptionFraction;
import com.electricitycompany.service.ConsumptionFractionService;
import com.electricitycompany.validator.BeforeCreateConsumptionFractionValidator;

@RestController
@RequestMapping("/consumptionfractions")
public class ConsumptionFractionController {

	@Autowired
	private ConsumptionFractionService fractionService;

	@RequestMapping(value = "/addAll", method = RequestMethod.POST)
	public ResponseEntity<List<ConsumptionFraction>> addAll(@Valid @RequestBody List<ConsumptionFraction> fractions,
			Errors errors) throws ServiceException {
		if (errors.hasErrors()) {
			throw new ServiceException(errors);
		}
		fractions.forEach(fr -> fractionService.save(fr));
		return new ResponseEntity<List<ConsumptionFraction>>(fractions, HttpStatus.CREATED);
	}

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public List<ConsumptionFraction> getAll() {
		return fractionService.getAll();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ConsumptionFraction> get(@PathVariable("id") Long id) {
		ConsumptionFraction consumptionFraction = fractionService.getOne(id);
		return (consumptionFraction == null ? ResponseEntity.status(HttpStatus.NOT_FOUND) : ResponseEntity.ok())
				.body(consumptionFraction);

	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id) {
		fractionService.delete(id);
	}

	@RequestMapping(value = "/add", method = RequestMethod.PUT)
	public ResponseEntity<ConsumptionFraction> save(ConsumptionFraction fraction) {
		ConsumptionFraction fr = fractionService.save(fraction);
		return ResponseEntity.ok().body(fr);
	}

	@InitBinder()
	public void setupBinder(WebDataBinder binder) {
		binder.addValidators(new BeforeCreateConsumptionFractionValidator(fractionService.getFractionRepository()));
	}

}
