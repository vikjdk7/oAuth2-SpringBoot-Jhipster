package com.agosh.login.pojo;

import java.io.Serializable;
import java.time.Instant;

/**
 * A UserContactVO.
 */
public class UserContactVO extends CommonVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;

	private String name;

	private String contactEmail;

	private String loginUserEmail;

	private String relation;

	private Instant updatedOn;

	private String updatedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public UserContactVO name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public UserContactVO contactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
		return this;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getLoginUserEmail() {
		return loginUserEmail;
	}

	public UserContactVO loginUserEmail(String loginUserEmail) {
		this.loginUserEmail = loginUserEmail;
		return this;
	}

	public void setLoginUserEmail(String loginUserEmail) {
		this.loginUserEmail = loginUserEmail;
	}

	public String getRelation() {
		return relation;
	}

	public UserContactVO relation(String relation) {
		this.relation = relation;
		return this;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public Instant getUpdatedOn() {
		return updatedOn;
	}

	public UserContactVO updatedOn(Instant updatedOn) {
		this.updatedOn = updatedOn;
		return this;
	}

	public void setUpdatedOn(Instant updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public UserContactVO updatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
		return this;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof UserContactVO)) {
			return false;
		}
		return id != null && id.equals(((UserContactVO) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

}
