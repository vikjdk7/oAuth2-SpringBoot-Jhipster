package com.agosh.login.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A UserContact.
 */
@Document(collection = "user_contact")
public class UserContact implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("phone")
    private String phone;

    @NotNull
    @Field("contact_email")
    private String contactEmail;

    @NotNull
    @Field("login_user_email")
    private String loginUserEmail;

    @NotNull
    @Field("relation")
    private String relation;

    @NotNull
    @Field("address_1")
    private String address1;

    @Field("address_2")
    private String address2;

    @Field("landmark")
    private String landmark;

    @Field("cityId")
    private Integer cityId;

    @Field("stateId")
    private Integer stateId;

    @Field("countryId")
    private Integer countryId;

    @Field("zipcode")
    private String zipcode;

    @Field("updated_on")
    private Instant updatedOn;

    @Field("updated_by")
    private String updatedBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public UserContact name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public UserContact phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public UserContact contactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
        return this;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getLoginUserEmail() {
        return loginUserEmail;
    }

    public UserContact loginUserEmail(String loginUserEmail) {
        this.loginUserEmail = loginUserEmail;
        return this;
    }

    public void setLoginUserEmail(String loginUserEmail) {
        this.loginUserEmail = loginUserEmail;
    }

    public String getRelation() {
        return relation;
    }

    public UserContact relation(String relation) {
        this.relation = relation;
        return this;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getAddress1() {
        return address1;
    }

    public UserContact address1(String address1) {
        this.address1 = address1;
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public UserContact address2(String address2) {
        this.address2 = address2;
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getLandmark() {
        return landmark;
    }

    public UserContact landmark(String landmark) {
        this.landmark = landmark;
        return this;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    
    public String getZipcode() {
        return zipcode;
    }

    public UserContact zipcode(String zipcode) {
        this.zipcode = zipcode;
        return this;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public UserContact updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public UserContact updatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here
    
    

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserContact)) {
            return false;
        }
        return id != null && id.equals(((UserContact) o).id);
    }

    public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	@Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserContact{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", phone='" + getPhone() + "'" +
            ", contactEmail='" + getContactEmail() + "'" +
            ", loginUserEmail='" + getLoginUserEmail() + "'" +
            ", relation='" + getRelation() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", landmark='" + getLandmark() + "'" +
            ", zipcode='" + getZipcode() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
