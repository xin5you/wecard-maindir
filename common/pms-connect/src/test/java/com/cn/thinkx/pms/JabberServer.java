package com.cn.thinkx.pms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;

import org.apache.commons.io.FileUtils;
public class JabberServer {

	public static int PORT = 10404;
	public static void main(String[] agrs) {
		ServerSocket s = null;
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			s = new ServerSocket(PORT);
			System.out.println("ServerSocket Start:"+s);
			//等待请求,此方法会一直阻塞,直到获得请求才往下走
			socket = s.accept();
			System.out.println("Connection accept socket:"+socket);
			//用于接收客户端发来的请求
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//用于发送返回信息,可以不需要装饰这么多io流使用缓冲流时发送数据要注意调用.flush()方法
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
			CharBuffer cb = CharBuffer.allocate(8192);
			int charIndex=-1;
			while ((charIndex=br.read(cb))!=-1) {
				cb.flip();
				System.out.println(cb.toString());
				if (cb.length()>5) {
					FileUtils.writeStringToFile(new File("c:/1.txt"), cb.toString());
//					System.out.println(StringUtil.getFormattedNum("",cb.length() ));
					//客户端socket指定服务器的地址和端口号
					socket = new Socket("127.0.0.1", 10102);
					System.out.println("Socket=" + socket);
					//同服务器原理一样
					br = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));
					pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
							socket.getOutputStream())));
					pw.println(cb.toString());
					pw.flush();
				}
			}
			System.out.println("success");
//			while(true){
//				
//				String str = br.readLine();
//				if(str.equals("END")){
//					break;
//				}
//				System.out.println("Client Socket Message:"+str);
//				Thread.sleep(1000);
//				pw.println("Message Received");
//				pw.flush();
//			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			System.out.println("Close.....");
			try {
				br.close();
				pw.close();
				socket.close();
				s.close();
			} catch (Exception e2) {
				
			}
		}
	}
}
