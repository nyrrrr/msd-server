package com.nyrrrr.msd.server;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Server used for receiving motion sensor datalogs from victim app
 * 
 * @author nyrrrr
 *
 */
public class SocketServer {

	private static int iPortNumber;
	private static Socket socket;

	private static BufferedReader reader;
	private static PrintWriter writer;
	private static FileOutputStream fileOutPutStream;

	// private static String outputFileDestination = "C:\\git\\data-thesis\\";
	private static String outputFileDestination = "C:\\Users\\nyrrrr\\Desktop\\data\\";

	public static void main(String args[]) {
		iPortNumber = Integer.parseInt(args[0]);

		try {
			ServerSocket serverSocket = new ServerSocket(iPortNumber);
			System.out.println("Server started and listening on port: " + iPortNumber);

			int bufferSize = 16384;
			String fileName = "";
			String message = "";
			String checkString = "";

			while (true) { // always running
				socket = serverSocket.accept();

				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer = new PrintWriter(socket.getOutputStream(), true);
				message = reader.readLine();

				
				
				// transfer protocol
				if (message.equals("FILE")) {
					System.out.println("Mobile device connected.");
					writer.println("File name?");
					writer.flush();
					fileName = reader.readLine();
					if (fileName.contains(".csv")) {
						writer.println("File size?");
						writer.flush();
						checkString = reader.readLine();
						if(checkString.equals("Abort")) continue;
						bufferSize = Integer.parseInt(checkString);
						System.out.println("Waiting for file...");
						writer.println("Waiting for file...");
						writer.flush();
						receiveFile(outputFileDestination, fileName, bufferSize);
					} else {
						writer.println("400");
						writer.flush();
					}
				} else {
					writer.println("400");
					writer.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param outputFileDestination
	 * @param fileName
	 * @param bufferSize
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static void receiveFile(String outputFileDestination, String fileName, int bufferSize)
			throws IOException, FileNotFoundException {
		int bytesRead;
		int offset = 0;
		char[] charArray = new char[bufferSize];

		fileOutPutStream = new FileOutputStream(outputFileDestination + fileName);

		while ((bytesRead = reader.read(charArray, offset, bufferSize - offset)) > 0) {
			offset += bytesRead;

		}
		PrintWriter fileWriter = new PrintWriter(fileOutPutStream);
		fileWriter.write(charArray, 0, offset);
		fileWriter.flush();
		System.out.println(fileName + " successfully stored");
		writer.println("File successfully stored");
		writer.flush();
	}

}
