package com.agosh.login.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A BankAccount.
 */
@Document(collection = "bank_account")
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("user_id")
    private String userId;

    @NotNull
    @Field("user_email")
    private String userEmail;

    @NotNull
    @Field("bank_name")
    private String bankName;

    @NotNull
    @Field("ifsc_code")
    private String ifscCode;

    @NotNull
    @Field("branch")
    private String branch;

    @NotNull
    @Field("account_holder")
    private String accountHolder;

    @NotNull
    @Field("account_number")
    private String accountNumber;

    @Field("city_id")
    private int cityId;

    @Field("state_id")
    private int stateId;

    @Field("country_id")
    private int countryId;

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

    public String getUserId() {
        return userId;
    }

    public BankAccount userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public BankAccount userEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBankName() {
        return bankName;
    }

    public BankAccount bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public BankAccount ifscCode(String ifscCode) {
        this.ifscCode = ifscCode;
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranch() {
        return branch;
    }

    public BankAccount branch(String branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public BankAccount accountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
        return this;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BankAccount accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getCityId() {
        return cityId;
    }

    public BankAccount cityId(int cityId) {
        this.cityId = cityId;
        return this;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getStateId() {
        return stateId;
    }

    public BankAccount stateId(int stateId) {
        this.stateId = stateId;
        return this;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getCountryId() {
        return countryId;
    }

    public BankAccount countryId(int countryId) {
        this.countryId = countryId;
        return this;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public BankAccount updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public BankAccount updatedBy(String updatedBy) {
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
        if (!(o instanceof BankAccount)) {
            return false;
        }
        return id != null && id.equals(((BankAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BankAccount{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", userEmail='" + getUserEmail() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", ifscCode='" + getIfscCode() + "'" +
            ", branch='" + getBranch() + "'" +
            ", accountHolder='" + getAccountHolder() + "'" +
            ", accountNumber='" + getAccountNumber() + "'" +
            ", cityId='" + getCityId() + "'" +
            ", stateId='" + getStateId() + "'" +
            ", countryId='" + getCountryId() + "'" +
            ", updatedOn='" + getUpdatedOn() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            "}";
    }
}
