package com.xyz.hayhay.ping;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class PingClient {
	public static void main(String[] args) throws FileNotFoundException {
		File f = new File("/kientv/webcollector/ping.log");
		FileOutputStream out = new FileOutputStream(f);
		try {
			Socket clientSocket = new Socket("localhost", 8088);
			InputStream in = null;
			try {
				in = clientSocket.getInputStream();
				byte[] buffer = new byte[1024];
				while (in.read(buffer) > 0) {
					String msg = new String(buffer);
					out.write(buffer);
					System.out.print(msg);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (in != null)
						in.close();
					out.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			clientSocket.close();
		} catch (Exception e) {
			System.out.print("down");
			try {
				out.write("down".getBytes());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
