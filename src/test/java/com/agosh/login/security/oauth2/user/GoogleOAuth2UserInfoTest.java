package com.agosh.login.security.oauth2.user;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GoogleOAuth2UserInfoTest {

	@Test
	public void getId() throws Exception {
		// setup
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("sub", "sub value");
		attributes.put("picture", "picture url");
		GoogleOAuth2UserInfo googleOAuth2UserInfo = new GoogleOAuth2UserInfo(attributes);
		String id = googleOAuth2UserInfo.getId();
		String picture = googleOAuth2UserInfo.getImageUrl();
		assertEquals("sub value", id);
		assertEquals("picture url", picture);
	}

}
