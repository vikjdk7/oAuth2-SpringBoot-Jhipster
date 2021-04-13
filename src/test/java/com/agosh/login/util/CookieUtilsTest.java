package com.agosh.login.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

@ExtendWith(MockitoExtension.class)
public class CookieUtilsTest {


	@Test
	public void getCookie() {
		Cookie[] cookies = new Cookie[1];
		Cookie cookie = new Cookie("name", "value");
		cookies[0] = cookie;
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(cookies);
		Optional<Cookie> cookieOpt = CookieUtils.getCookie(request, "name");
		assertTrue(cookieOpt.isPresent());
	}
	
	@Test
	public void getNotExistingCookie() {
		Cookie[] cookies = new Cookie[1];
		Cookie cookie = new Cookie("name", "value");
		cookies[0] = cookie;
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(cookies);
		Optional<Cookie> cookieOpt = CookieUtils.getCookie(request, "name2");
		assertFalse(cookieOpt.isPresent());
	}
	
	@Test
	public void addCookie() {
		
		HttpServletResponse httpServletResponse = new MockHttpServletResponse();
		CookieUtils.addCookie(httpServletResponse, "name", "value", 1000);
		assertTrue(true);
	}
	
	@Test
	public void deleteCookie() {
		Cookie[] cookies = new Cookie[1];
		Cookie cookie = new Cookie("name", "value");
		cookies[0] = cookie;
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setCookies(cookies);
		HttpServletResponse httpServletResponse = new MockHttpServletResponse();
		CookieUtils.deleteCookie(request, httpServletResponse, "name");
		assertTrue(true);
	}
	
	@Test
	public void serialize() {
		
		String value = CookieUtils.serialize("name");
		assertNotNull(value);
	}
	
	@Test
	public void deserialize() {
		String value = CookieUtils.serialize("name");
		Cookie cookie = new Cookie("name", value);
		String desValue = CookieUtils.deserialize(cookie, String.class);
		assertNotNull(desValue);
	}

}
