package com.xyz.messager;

public class Message {
	
	enum TYPE{TEXT,PROFILE}
	int type;
	byte[] content;
	String from;
	String to;
	
	public Message(int type, byte[] content, String from, String to) {
		super();
		this.type = type;
		this.content = content;
		this.from = from;
		this.to = to;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}

}
