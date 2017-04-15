package com.xyz.messager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketConnectionServer{
	private static Map<String, Client> sessionConversationMapping = new HashMap<String, Client>();
	private static Map<String, Client> profileConversationMapping = new HashMap<String, Client>();
	static ConversationListener conversationListener = new ConversationListener() {
		@Override
		public void onProfileReceived(Profile profile) {
			profileConversationMapping.put(profile.getId(), sessionConversationMapping.get(profile.getSessionId()));
		}
		@Override
		public void onConnectionClose(String sessionId, String profileId) {
			sessionConversationMapping.remove(sessionId);
			profileConversationMapping.remove(profileId);
		}
		@Override
		public void onMessageReceived(String message) {
		}
		@Override
		public void onSendMessage(Message msg) {
		}
	};
	public static void startSocketConnectionServer(){
		ServerSocket svSocket = null;
		try{
			svSocket = new ServerSocket(8080);
			while(true){
				try {
					System.out.println("Waiting for connection...");
					Socket clientSocket = svSocket.accept();
					String session = System.nanoTime() + "";
					Client conversation = new Client(session,clientSocket, conversationListener);
					sessionConversationMapping.put(session, conversation);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}catch(Exception ex){
			
		}finally {
			if(svSocket != null && !svSocket.isClosed())
				try {
					svSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

	}
	public static void main(String[] args) {
		SocketConnectionServer.startSocketConnectionServer();
	}
	
}
