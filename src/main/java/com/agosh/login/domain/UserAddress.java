package com.agosh.login.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.agosh.login.domain.enumeration.AddressType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * A UserAddress.
 */
@Document(collection = "user_address")
public class UserAddress extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Field("login_id")
	private String loginId;

	@NotNull
	@Field("address_1")
	private String address1;

	@Field("address_2")
	private String address2;

	@Field("landmark")
	private String landmark;

	@NotNull
	@Field("zipcode")
	private String zipcode;

	@Field("address_type")
	private AddressType addressType;

	@Field("location_name")
	private String locationName;

	@Field("phone")
	private String phone;

	@Field("website")
	private String website;

	@Field("business_hour")
	private String businessHour;

	@DBRef
	@Field("loginUser")
	@JsonIgnoreProperties(value = "userAddresses", allowSetters = true)
	private LoginUser loginUser;

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginId() {
		return loginId;
	}

	public UserAddress loginId(String loginId) {
		this.loginId = loginId;
		return this;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getAddress1() {
		return address1;
	}

	public UserAddress address1(String address1) {
		this.address1 = address1;
		return this;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public UserAddress address2(String address2) {
		this.address2 = address2;
		return this;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getLandmark() {
		return landmark;
	}

	public UserAddress landmark(String landmark) {
		this.landmark = landmark;
		return this;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getZipcode() {
		return zipcode;
	}

	public UserAddress zipcode(String zipcode) {
		this.zipcode = zipcode;
		return this;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public UserAddress addressType(AddressType addressType) {
		this.addressType = addressType;
		return this;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	public String getLocationName() {
		return locationName;
	}

	public UserAddress locationName(String locationName) {
		this.locationName = locationName;
		return this;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getPhone() {
		return phone;
	}

	public UserAddress phone(String phone) {
		this.phone = phone;
		return this;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebsite() {
		return website;
	}

	public UserAddress website(String website) {
		this.website = website;
		return this;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getBusinessHour() {
		return businessHour;
	}

	public UserAddress businessHour(String businessHour) {
		this.businessHour = businessHour;
		return this;
	}

	public void setBusinessHour(String businessHour) {
		this.businessHour = businessHour;
	}

	public LoginUser getLoginUser() {
		return loginUser;
	}

	public UserAddress loginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
		return this;
	}

	public void setLoginUser(LoginUser loginUser) {
		this.loginUser = loginUser;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UserAddress)) {
			return false;
		}
		return id != null && id.equals(((UserAddress) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "UserAddress{" + "id=" + getId() + ", loginId='" + getLoginId() + "'" + ", address1='" + getAddress1()
				+ "'" + ", address2='" + getAddress2() + "'" + ", landmark='" + getLandmark() + "'" + ", zipcode='"
				+ getZipcode() + "'" + ", addressType='" + getAddressType() + "'" + ", locationName='"
				+ getLocationName() + "'" + ", phone='" + getPhone() + "'" + ", website='" + getWebsite() + "'"
				+ ", businessHour='" + getBusinessHour() + "'" + "}";
	}
}
