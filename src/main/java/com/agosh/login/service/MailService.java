package com.agosh.login.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.agosh.login.pojo.FpToken;
import com.agosh.login.pojo.PublishMessage;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Service for sending emails.
 */
@Service
public class MailService {

	private final Logger log = LoggerFactory.getLogger(MailService.class);

	private final KafkaService kafkaService;

	private ObjectMapper mapper = new ObjectMapper();

	public MailService(KafkaService kafkaService) {

		this.kafkaService = kafkaService;
	}

	@Async
	public void sendNotification(String topicName, PublishMessage publishMessage) {

		try {
			String message = mapper.writeValueAsString(publishMessage);
			this.kafkaService.publish(topicName, message, "mykey");
		} catch (Exception e) {
			log.warn("Message could not be published:", e);
		}
	}

	public String prepareForgotPwdEmailBody(FpToken fpToken) {

		String urlLink = "https://www.agosh.com/resetpassword" + "?key=" + fpToken.getKey();
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<head>");
		setButtonStyle(sb);
		sb.append("<head>");
		sb.append("<table border='0'>");
		sb.append("<tr><td>Hello&nbsp;");
		sb.append(StringUtils.hasText(fpToken.getFirstName()) ? fpToken.getFirstName() : "");
		sb.append("&nbsp;");
		sb.append(StringUtils.hasText(fpToken.getLastName()) ? fpToken.getLastName() : "");
		sb.append("<br/>&nbsp;</td></tr>");
		sb.append("<tr><td>Kindly click on \"Reset Password\" button to change your password.<br/><br/>");
		sb.append("<div class=\"btn btn-primary\" >");
		sb.append("<a style=\"color:white;\" href=\"" + urlLink + "\"> Reset Password</a>");
		sb.append("</div>");
		sb.append("<br/>&nbsp;</td></tr>");
		sb.append("<tr><td>Thanks.<br/>Agosh Administrator</td></tr>");
		sb.append("<tr><td><hr></td></tr>");
		sb.append("</table>");
		sb.append("</html>");
		return sb.toString();
	}

	private void setButtonStyle(StringBuilder sb) {
		sb.append("<style>");
		sb.append("a {");
		sb.append("text-decoration: none !important;");
		sb.append("}");
		sb.append(".btn-primary {");
		sb.append("color: #fff;");
		sb.append("text-shadow: 0 -1px 0 rgba(0,0,0,0.25);");
		sb.append("background-color: #006dcc;");
		sb.append("background-image: linear-gradient(to bottom,#08c,#04c);");
		sb.append("background-repeat: repeat-x;");
		sb.append("border-color: rgba(0,0,0,0.1) rgba(0,0,0,0.1) rgba(0,0,0,0.25);");
		sb.append("}");
		sb.append(".btn {");
		sb.append("display: inline-block;");
		sb.append("padding: 4px 12px;");
		sb.append("margin-bottom: 0;");
		sb.append("font-size: 14px;");
		sb.append("line-height: 20px;");
		sb.append("text-align: center;");
		sb.append("vertical-align: middle;");
		sb.append("cursor: pointer;");
		sb.append("border: 1px solid #ccc;");
		sb.append("border-radius: 4px;");
		sb.append("box-shadow: inset 0 1px 0 rgba(255,255,255,0.2), 0 1px 2px rgba(0,0,0,0.05);");
		sb.append("}");
		sb.append("</style>");
	}

}
