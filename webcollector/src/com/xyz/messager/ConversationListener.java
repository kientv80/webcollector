package com.xyz.messager;

public interface ConversationListener {
	public void onProfileReceived(Profile profile);
	public void onMessageReceived(String message);
	public void onSendMessage(Message msg);
	public void onConnectionClose(String sessionId, String profileId);
}
