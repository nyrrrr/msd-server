package com.nyrrrr.msd.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server used for receiving motion sensor datalogs from victim
 * 
 * @author nyrrrr
 *
 */
public class SocketServer {

	private static int iPortNumber;

	public static void main(String args[]) {
		iPortNumber = Integer.parseInt(args[0]);
		System.out.println("Try to start server on port: " + iPortNumber);

		try (ServerSocket serverSocket = new ServerSocket(iPortNumber);
				Socket clientSocket = serverSocket.accept();
				// only listens to one client at a time
				PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(clientSocket.getInputStream()));) {

			System.out.println("Connection established");
			String input;
			
			// TODO set up protocoll
			
			// TODO handle input
			while ((input = bufferedReader.readLine()) != null) {
				
				
				if(true) break; // TODO end condition
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
