package com.xyz.messager;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {
	public void openConnection(){
		try {
			Socket s = new Socket("localhost",8080);
			for(int i =0;i< 5;i++){
				s.getOutputStream().write(("Hello server " + i).getBytes());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			s.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		new ClientSocket().openConnection();
	}
}
