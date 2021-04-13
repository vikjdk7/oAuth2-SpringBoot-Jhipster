package com.agosh.login.pojo;

import java.util.Map;

import com.agosh.login.domain.enumeration.AuthProvider;
import com.agosh.login.domain.enumeration.UserStatus;
import com.agosh.login.domain.enumeration.UserType;

public class SignUpRequest extends CommonVO {

	private String id;

	private String password;

	private String firstName;

	private String lastName;

	private String middleName;

	private String imageUrl;

	private Boolean emailVerified;

	private AuthProvider provider;

	private String providerId;

	private UserType userType;

	private UserStatus userStatus;

	// Seller's fields
	private String businessName;
	private String businessWebsite;
	private String tagLine;
	private String logo;
	private String businessLicNo;
	private String businessType;
	private Map<String, String> taxIds;
	private int nosLocations;

	// Buyer's fields
	private String dateOfBirth;

	// Partner's fields
	private int locationId;
	private String requestMessage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public AuthProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getBusinessWebsite() {
		return businessWebsite;
	}

	public void setBusinessWebsite(String businessWebsite) {
		this.businessWebsite = businessWebsite;
	}

	public String getTagLine() {
		return tagLine;
	}

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getBusinessLicNo() {
		return businessLicNo;
	}

	public void setBusinessLicNo(String businessLicNo) {
		this.businessLicNo = businessLicNo;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Map<String, String> getTaxIds() {
		return taxIds;
	}

	public void setTaxIds(Map<String, String> taxIds) {
		this.taxIds = taxIds;
	}

	public int getNosLocations() {
		return nosLocations;
	}

	public void setNosLocations(int nosLocations) {
		this.nosLocations = nosLocations;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getRequestMessage() {
		return requestMessage;
	}

	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

}
