package com.agosh.login.pojo;

public class PublishMessage {
	public final String subject;
	public final String body;
	public final String from;
	public final String to;
	public final String type;

	public PublishMessage(String subject, String body, String from, String to, String type) {
		this.subject = subject;
		this.body = body;
		this.from = from;
		this.to = to;
		this.type = type;
	}
}