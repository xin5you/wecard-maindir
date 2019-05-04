package com.cn.thinkx.pms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class TCPServer {

	// Size of receive buffer
	private static final int BUFSIZE = 32;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int servPort = 9001;
		ServerSocket servSocket = null;
		int recvMsgSize = 0;
		byte[] receivBuf = new byte[BUFSIZE];

		try {
			servSocket = new ServerSocket(servPort);
			while (true) {
				Socket clientSocket = servSocket.accept();
				SocketAddress clientAddress = clientSocket.getRemoteSocketAddress();
				System.out.println("Handling client at " + clientAddress);

				InputStream in = clientSocket.getInputStream();
				OutputStream out = clientSocket.getOutputStream();

				// receive until client close connection,indicate by -l return
				while ((recvMsgSize = in.read(receivBuf)) != -1) {
					String receivedData = new String(receivBuf.toString());
					System.out.println(receivedData);
					out.write(receivBuf, 0, recvMsgSize);
				}
				clientSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
