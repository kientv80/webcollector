package com.xyz.hayhay.ping;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.xyz.hayhay.website.collector.CollectorManager;

public class PingServer {
	public static void startPingServer(){
		ServerSocket svSocket = null;
		try{
			svSocket = new ServerSocket(8088);
			while(true){
				try {
					System.out.println("Waiting for connection...");
					Socket clientSocket = svSocket.accept();
					OutputStream out = clientSocket.getOutputStream();
					out.write("alive".getBytes());
//					if((System.currentTimeMillis()- CollectorManager.lastTimeCollected) < CollectorManager.COLLECTING_PERIOD){
//						out.write("alive".getBytes());
//					}else{
//						out.write("die".getBytes());
//					}
					out.close();
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
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
		PingServer.startPingServer();
	}
}
