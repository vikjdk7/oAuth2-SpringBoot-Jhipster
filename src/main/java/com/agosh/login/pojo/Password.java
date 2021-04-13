package com.agosh.login.pojo;

import javax.validation.constraints.NotBlank;

public class Password {

	@NotBlank
	private String id;

	@NotBlank
	private String newPassword;
	
	@NotBlank
	private String currentPassword;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getCurrentPassword() {
		return currentPassword;
	}

	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	

}
