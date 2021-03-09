package com.chatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	Socket socket;
	
	BufferedReader br; //for reading the data
	PrintWriter out; //for writing the data
	
	
	public Client() {
	
		try {
			System.out.println("Sending request to server");
			socket = new Socket("127.0.0.1", 9876); //As server is running in the same computer and the port no for the communication channel
			System.out.println("Connection established");
			
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			//here the data we receive from the client is read in form of bytes and then it is converted by using BufferedReader
			
			out = new PrintWriter(socket.getOutputStream()); //for the output purpose
			
			read(); //after getting the data from br, read
			write();
			
		}
		
		catch(Exception e) {
			
		}
	}
	
	public void read() {
		
		Runnable r1 = () -> {
			
			System.out.println("reader started");
			
			try {
			while (true) {
				
				String msg;
					msg = br.readLine();
					
					if (msg == "quit") {
						
						System.out.println("Server terminated the chat !!!!!");
						socket.close();
						break;
					}
					
					System.out.println("Server: " +msg);
				}
			}
			catch (IOException e) {
				
					e.printStackTrace();
				}
				
		};
		
		new Thread(r1).start(); //to start the thread r1 that we have created
	}
	
	public void write() {
		
		Runnable r2 = () -> {
			
			System.out.println("Writer started");
			
			try {
			while (true) {
				
					BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
					String output = br1.readLine();
					out.println(output);
					out.flush();
					
					
				} 
			
			}
			catch (IOException e) {
					
					e.printStackTrace();
				}
				
		};
		
		new Thread(r2).start(); //to start the thread r2 that we have created
		
	}
	

	public static void main(String[] args) {
		
		System.out.println("Starting the client");
		new Client();
	}

}
