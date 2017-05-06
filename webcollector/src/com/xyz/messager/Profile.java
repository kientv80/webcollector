package com.xyz.messager;

public class Profile {
	String id;
	private String sessionId;
	String nickName;
	String displayName;
	String avatarUrl;
	public Profile(String id, String nickName, String displayName, String avatarUrl) {
		super();
		this.id = id;
		this.nickName = nickName;
		this.displayName = displayName;
		this.avatarUrl = avatarUrl;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
