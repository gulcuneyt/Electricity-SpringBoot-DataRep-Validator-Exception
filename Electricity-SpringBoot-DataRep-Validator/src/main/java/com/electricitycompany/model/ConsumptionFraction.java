package com.electricitycompany.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ConsumptionFraction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long profileId;
	private Short month;
	private Double value;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProfileId() {
		return profileId;
	}
	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}
	public Short getMonth() {
		return month;
	}
	public void setMonth(Short month) {
		this.month = month;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}

}