package com.agosh.login.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.agosh.login.domain.enumeration.AuthProvider;
import com.agosh.login.domain.enumeration.UserStatus;
import com.agosh.login.domain.enumeration.UserType;

/**
 * A LoginUser.
 */
@Document(collection = "login_user")
public class LoginUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Field("login_id")
	private String loginId;

	@Field("password")
	private String password;

	@NotNull
	@Field("name")
	private String name;

	@Field("first_name")
	private String firstName;

	@Field("last_name")
	private String lastName;

	@Field("middle_name")
	private String middleName;

	@Field("phone")
	private String phone;

	@NotNull
	@Field("email")
	private String email;

	@Field("image_url")
	private String imageUrl;

	@Field("date_of_birth")
	private LocalDate dateOfBirth;

	@Field("email_verified")
	private Boolean emailVerified;

	@Field("provider")
	private AuthProvider provider;

	@Field("provider_id")
	private String providerId;

	@Field("city_id")
	private String cityId;

	@Field("state_id")
	private String stateId;

	@Field("country_id")
	private String countryId;

	@Field("partner_user_name")
	private String partnerUserName;

	@Field("user_type")
	private UserType userType;

	@Field("user_status")
	private UserStatus userStatus;

	@Field("updated_on")
	private LocalDate updatedOn;

	@Field("updated_by")
	private String updatedBy;

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

	public LoginUser loginId(String loginId) {
		this.loginId = loginId;
		return this;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public LoginUser password(String password) {
		this.password = password;
		return this;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public LoginUser name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public LoginUser firstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public LoginUser lastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public LoginUser middleName(String middleName) {
		this.middleName = middleName;
		return this;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getPhone() {
		return phone;
	}

	public LoginUser phone(String phone) {
		this.phone = phone;
		return this;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public LoginUser email(String email) {
		this.email = email;
		return this;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public LoginUser imageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
		return this;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean isEmailVerified() {
		return emailVerified;
	}

	public LoginUser emailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
		return this;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public AuthProvider getProvider() {
		return provider;
	}

	public LoginUser provider(AuthProvider provider) {
		this.provider = provider;
		return this;
	}

	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public LoginUser providerId(String providerId) {
		this.providerId = providerId;
		return this;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public UserType getUserType() {
		return userType;
	}

	public LoginUser userType(UserType userType) {
		this.userType = userType;
		return this;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public LoginUser userStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
		return this;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDate updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getPartnerUserName() {
		return partnerUserName;
	}

	public void setPartnerUserName(String partnerUserName) {
		this.partnerUserName = partnerUserName;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getStateId() {
		return stateId;
	}

	public void setStateId(String stateId) {
		this.stateId = stateId;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof LoginUser)) {
			return false;
		}
		return id != null && id.equals(((LoginUser) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "LoginUser{" + "id=" + getId() + ", loginId='" + getLoginId() + "'" + ", password='" + getPassword()
				+ "'" + ", name='" + getName() + "'" + ", firstName='" + getFirstName() + "'" + ", lastName='"
				+ getLastName() + "'" + ", middleName='" + getMiddleName() + "'" + ", phone='" + getPhone() + "'"
				+ ", email='" + getEmail() + "'" + ", imageUrl='" + getImageUrl() + "'" + ", emailVerified='"
				+ isEmailVerified() + "'" + ", provider='" + getProvider() + "'" + ", providerId='" + getProviderId()
				+ "'" + ", userType='" + getUserType() + "'" + ", userStatus='" + getUserStatus() + "'" + "}";
	}
}
