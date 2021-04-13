package com.agosh.login.security.oauth2.user;

import java.util.Map;

import com.agosh.login.domain.enumeration.AuthProvider;
import com.agosh.login.web.rest.errors.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactory {

	private OAuth2UserInfoFactory() {
	}

	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())) {
			return new GoogleOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())) {
			return new FacebookOAuth2UserInfo(attributes);
		} else if (registrationId.equalsIgnoreCase(AuthProvider.github.toString())) {
			return new GithubOAuth2UserInfo(attributes);
		} else {
			throw new OAuth2AuthenticationProcessingException(
					"Sorry! Login with " + registrationId + " is not supported yet.");
		}
	}
}
