package com.xyz.messager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client{
	Socket clientSocket;
	String clientId;
	String sessionId;
	ConversationListener listener;

	public Client(String sessionId, Socket clientSocket, ConversationListener listener) {
		this.clientSocket = clientSocket;
		this.sessionId = sessionId;
		this.listener = listener;
		startConversation();
	}
	private  void startConversation(){
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				InputStream in = null;
				OutputStream out = null;
				try {
					in = clientSocket.getInputStream();
					out = clientSocket.getOutputStream();
					byte[] buffer = new byte[1024];
					
					while(in.read(buffer) > 0){
						Message msg = (Message)JSONHelper.fromJSONObject(Message.class, new String(buffer));
						listener.onSendMessage(msg);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					try {
					if(in != null)
						in.close();
					if(out != null)
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}
		});
		thread.start();
	}
	public Socket getClientSocket() {
		return clientSocket;
	}
	public void setClientSocket(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public void close(){
		try {
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
