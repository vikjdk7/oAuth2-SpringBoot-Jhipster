package com.agosh.login.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Partner.
 */
@Document(collection = "partner")
public class Partner extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Field("login_id")
	private String loginId;

	@Field("request_message")
	private String requestMessage;

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

	public Partner loginId(String loginId) {
		this.loginId = loginId;
		return this;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here

	public String getRequestMessage() {
		return requestMessage;
	}

	public void setRequestMessage(String requestMessage) {
		this.requestMessage = requestMessage;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Partner)) {
			return false;
		}
		return id != null && id.equals(((Partner) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Partner{" + "id=" + getId() + ", loginId='" + getLoginId() + "'" +

				"}";
	}
}
