package com.agosh.login.pojo;

import java.io.Serializable;

public class PartnerVO extends CommonVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String loginId;

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

}
