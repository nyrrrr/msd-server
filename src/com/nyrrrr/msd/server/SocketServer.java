package com.nyrrrr.msd.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
	private static Socket socket;

	private static BufferedOutputStream bufferedOutputStream;
	private static FileOutputStream fileOutPutStream;
	private static InputStream inputStream;

	private static int bufferSize = 2000000000;

	public static void main(String args[]) {
		iPortNumber = Integer.parseInt(args[0]);

		try {
			ServerSocket serverSocket = new ServerSocket(iPortNumber);
			System.out.println("Server started and listening on port: " + iPortNumber);

			String outputFileDestination = "C:\\Users\\nyrrrr\\Desktop\\test.csv";

			while (true) { // always running
				socket = serverSocket.accept();

				byte[] byteArray = new byte[bufferSize];
				int offset = 0;
				int bytesRead;

				inputStream = socket.getInputStream();
				fileOutPutStream = new FileOutputStream(outputFileDestination);
				bufferedOutputStream = new BufferedOutputStream(fileOutPutStream);

				while ((bytesRead = inputStream.read(byteArray, offset, bufferSize - offset)) > 0) {
					offset += bytesRead;

				}

				bufferedOutputStream.write(byteArray, 0, offset);
				bufferedOutputStream.flush();
				System.out.println("Stored");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
