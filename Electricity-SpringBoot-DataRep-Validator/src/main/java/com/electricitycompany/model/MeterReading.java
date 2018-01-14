package com.electricitycompany.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MeterReading {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long meterId;
	private Short month;
	private Long amount;
	private boolean validated = true;

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMeterId() {
		return meterId;
	}

	public void setMeterId(Long meterId) {
		this.meterId = meterId;
	}

	public Short getMonth() {
		return month;
	}

	public void setMonth(Short month) {
		this.month = month;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

}
