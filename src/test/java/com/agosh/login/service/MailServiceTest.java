package com.agosh.login.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agosh.login.pojo.FpToken;
import com.agosh.login.pojo.PublishMessage;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {

	private static final String EMAIL = "abc@abc.com";
	
	@InjectMocks
	private MailService mailService;

	@Mock
	private KafkaService kafkaService;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void prepareForgotPwdEmailBody() throws Exception {
		// setup
		FpToken fpToken = new FpToken();
		fpToken.setEmail(EMAIL);

		String emailBody = mailService.prepareForgotPwdEmailBody(fpToken);
		assertNotNull(emailBody);
	}
	
	@Test
	public void sendNotification() throws Exception {
		// setup
		PublishMessage publishMessage = new PublishMessage("subject", "body", "from", "to", "type");

		mailService.sendNotification("token", publishMessage);
		assertTrue(true);
	}
}
