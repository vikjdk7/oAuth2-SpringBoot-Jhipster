package com.agosh.login.pojo;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

/**
 * A UserAddress.
 */
public class UserAddressVO extends CommonVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String loginId = StringUtils.EMPTY;

	private String website = StringUtils.EMPTY;

	private String businessHour = StringUtils.EMPTY;

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

	public UserAddressVO loginId(String loginId) {
		this.loginId = loginId;
		return this;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getWebsite() {
		return website;
	}

	public UserAddressVO website(String website) {
		this.website = website;
		return this;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getBusinessHour() {
		return businessHour;
	}

	public UserAddressVO businessHour(String businessHour) {
		this.businessHour = businessHour;
		return this;
	}

	public void setBusinessHour(String businessHour) {
		this.businessHour = businessHour;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UserAddressVO)) {
			return false;
		}
		return id != null && id.equals(((UserAddressVO) o).id);
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
