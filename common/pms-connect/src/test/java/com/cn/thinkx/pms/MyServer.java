package com.cn.thinkx.pms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
	private ServerSocket serverSocket; // 
	private ExecutorService servicePool; // 线程池

	public MyServer(int port) {
		try {
			this.serverSocket = new ServerSocket(port);
			this.servicePool = Executors.newFixedThreadPool(5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new MyServer(10404).service(); 
	}

	public void service() {
		int i = 0;
		while (true) {
			try {
				Socket socket = this.serverSocket.accept(); // 接受到一个连接，并且返回一个客户端的Socket对象实例
				this.servicePool.execute(new Handler(socket));
				System.out
						.println("User " + i + " is connecting to the Server");
				i++;
			} catch (IOException e) {
				e.printStackTrace();
				this.servicePool.shutdown();
			}
		}
	}

	class Handler implements Runnable {
		private Socket socket;

		public Handler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				// 一个输入流，用于获取客户端传来的内容
				DataInputStream dis = new DataInputStream(
						this.socket.getInputStream());
				// 用于产生服务器准备响应的内容
				DataOutputStream dos = new DataOutputStream(this.socket.getOutputStream());
				String str;
				while (null != (str = dis.readUTF())) {
					System.out.println(str);
					if ("exit".equals(str)) {
						System.out.println("客户端发出中断请求");
						dos.writeUTF("服务器已经关闭本次连接.");
						dos.flush();
//						dos.writeUTF("exit"); // 
//						dos.flush();
						
						dos.close();
						dis.close();
						break;
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
