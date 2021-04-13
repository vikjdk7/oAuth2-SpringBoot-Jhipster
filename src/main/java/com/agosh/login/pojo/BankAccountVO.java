package com.agosh.login.pojo;

import java.io.Serializable;
import java.time.Instant;

/**
 * A BankAccountVO.
 */
public class BankAccountVO extends CommonVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String userId;

    private String userEmail;

    private String bankName;

    private String ifscCode;

    private String branch;

    private String accountHolder;

    private String accountNumber;

    

    private Instant updatedOn;

    private String updatedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public BankAccountVO userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public BankAccountVO userEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getBankName() {
        return bankName;
    }

    public BankAccountVO bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public BankAccountVO ifscCode(String ifscCode) {
        this.ifscCode = ifscCode;
        return this;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranch() {
        return branch;
    }

    public BankAccountVO branch(String branch) {
        this.branch = branch;
        return this;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public BankAccountVO accountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
        return this;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BankAccountVO accountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public BankAccountVO updatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
        return this;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public BankAccountVO updatedBy(String updatedBy) {
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
        if (!(o instanceof BankAccountVO)) {
            return false;
        }
        return id != null && id.equals(((BankAccountVO) o).id);
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
