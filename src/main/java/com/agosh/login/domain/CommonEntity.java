package com.agosh.login.domain;

import org.springframework.data.mongodb.core.mapping.Field;

public class CommonEntity {
	@Field("city_id")
	private int cityId;

	@Field("state_id")
	private int stateId;
	
	@Field("country_id")
	private int countryId;
	
	public int getCityId() {
		return cityId;
	}

	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public int getStateId() {
		return stateId;
	}

	public void setStateId(int stateId) {
		this.stateId = stateId;
	}

	public int getCountryId() {
		return countryId;
	}

	public void setCountryId(int countryId) {
		this.countryId = countryId;
	}

}
