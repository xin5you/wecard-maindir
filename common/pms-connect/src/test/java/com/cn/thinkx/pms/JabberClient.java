package com.cn.thinkx.pms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *************************************************************** 
 * 项目名称：JavaThread 程序名称：JabberClient 日期：2012-8-23 下午01:47:12 
 * 作者：
 * 模块： 
 * 描述： 
 * 备注：
 * ------------------------------------------------------------ 
 * 修改历史
 * 序号
 * 日期
 * 修改人
 * 修改原因
 * 修改备注：
 * 
 * @version
 *************************************************************** 
 */
public class JabberClient {

	/**
	 * 方法名：main 描述： 作者：白鹏飞 日期：2012-8-23 下午01:47:12
	 * 
	 * @param @param args
	 * @return void
	 */
	public static void main(String[] args) {
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			//客户端socket指定服务器的地址和端口号
			socket = new Socket("127.0.0.1", JabberServer.PORT);
			System.out.println("Socket=" + socket);
			//同服务器原理一样
			br = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())));
			for (int i = 0; i < 10; i++) {
				pw.println("howdy " + i);
				pw.flush();
				String str = br.readLine();
				System.out.println(str);
			}
			pw.println("END");
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("close......");
				br.close();
				pw.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
