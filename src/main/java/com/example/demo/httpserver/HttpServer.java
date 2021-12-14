package com.example.demo.httpserver;

import java.awt.Image;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.naming.ldap.SortKey;

import com.example.demo.httpserver.config.ConfigureationManager;
import com.example.demo.httpserver.config.Configurration;

public class HttpServer 
	{
		public static void main(String[] args) 
			{
				System.out.println("server시작");
			
				ConfigureationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
				Configurration configurration = ConfigureationManager.getInstance().getCurrentConfiguration();
				
				System.out.println("Using Port : " + configurration.getPort());
				System.out.println("Using WebRoot : " + configurration.getWebroot());
				
				try 
				{
					ServerSocket serverSocket = new ServerSocket(configurration.getPort());
					Socket socket = serverSocket.accept();
					
					InputStream inputStream = socket.getInputStream();
					OutputStream outputStream = socket.getOutputStream();
					
					String html = "<html><head><title>Simply Java HTTP Server</title></head><body><h1>This page was served using Simple Java HTTP</h1></body></html>"; 
					
					final String CRLF = "\n\r"; // 13 , 10
					
					String response = 
							"HTTP/1.1 200 OK"+CRLF+ // Status Line : HTTP Version Response_Code Response_Message
							"Content-Length : "+html.getBytes().length + CRLF +  //Header
								CRLF +
								html+
								CRLF+CRLF;
					
					outputStream.write(response.getBytes());

					inputStream.close();
					outputStream.close();
					socket.close();
					serverSocket.close();
				} 
				catch (Exception e) 
				{
					// TODO: handle exception
				}
			}
	}
