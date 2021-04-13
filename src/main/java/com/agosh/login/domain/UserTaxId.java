package com.agosh.login.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * A State.
 */
@Document(collection = "UserTaxId")
public class UserTaxId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Field("login_id")
	private String loginId;

	@Field("tax_id_name")
	private String taxIdName;

	@Field("tax_id_value")
	private String taxIdValue;

	// jhipster-needle-entity-add-field - JHipster will add fields here
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UserTaxId)) {
			return false;
		}
		return id != null && id.equals(((UserTaxId) o).id);
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getTaxIdName() {
		return taxIdName;
	}

	public void setTaxIdName(String taxIdName) {
		this.taxIdName = taxIdName;
	}

	public String getTaxIdValue() {
		return taxIdValue;
	}

	public void setTaxIdValue(String taxIdValue) {
		this.taxIdValue = taxIdValue;
	}

	@Override
	public int hashCode() {
		return 31;
	}

	@Override
	public String toString() {
		return "UserTaxId [id=" + id + ", loginId=" + loginId + ", taxIdName=" + taxIdName + "]";
	}

}
