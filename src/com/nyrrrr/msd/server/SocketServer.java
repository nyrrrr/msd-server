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

		try {
			ServerSocket serverSocket = new ServerSocket(iPortNumber);
			System.out.println("Server started and listening on port: " + iPortNumber);

			while (true) { // always running
				Socket socket = serverSocket.accept();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(socket.getInputStream()));

				System.out.println("Received a message from client: " + bufferedReader.readLine());
				
				PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
				writer.println("TEST");
				writer.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
