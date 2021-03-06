package com.nyrrrr.msd.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	private static Socket oSocket;
	private static ServerSocket oServerSocket;

	private static BufferedReader oReader;
	private static PrintWriter oWriter;
	private static FileOutputStream oFileOutPutStream;

	private static String sOutputFileDestination = "C:\\git\\data-thesis\\R\\datasets\\";

	public static void main(String args[]) {
		iPortNumber = Integer.parseInt(args[0]);

		try {
			oServerSocket = new ServerSocket(iPortNumber);
			System.out.println("Server started and listening on port: " + iPortNumber);

			int bufferSize = 16384;
			String fileName = "";
			String message = "";
			String checkString = "";

			while (true) { // always running
				oSocket = oServerSocket.accept();

				oReader = new BufferedReader(new InputStreamReader(oSocket.getInputStream()));
				oWriter = new PrintWriter(oSocket.getOutputStream(), true);
				message = oReader.readLine();
				
				if(message == null) {
					continue;
				}
				// transfer protocol
				if (message.equals("FILE")) {
					System.out.println("Mobile device connected.");
					oWriter.println("File name?");
					oWriter.flush();
					fileName = oReader.readLine();
					if (fileName.contains("victim-data.csv")) {
						oWriter.println("File size?");
						oWriter.flush();
						checkString = oReader.readLine();
						if(checkString.equals("Abort")) continue;
						bufferSize = Integer.parseInt(checkString);
						System.out.println("Waiting for file...");
						oWriter.println("Waiting for file...");
						oWriter.flush();
						receiveFile(sOutputFileDestination, "transferred-" + fileName, bufferSize);
					} else {
						oWriter.println("400");
						oWriter.flush();
					}
				} else {
					oWriter.println("400");
					oWriter.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			System.err.println("Client has sent incorrect messages");
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

		oFileOutPutStream = new FileOutputStream(outputFileDestination + fileName);

		while ((bytesRead = oReader.read(charArray, offset, bufferSize - offset)) > 0) {
			offset += bytesRead;

		}
		PrintWriter fileWriter = new PrintWriter(oFileOutPutStream);
		fileWriter.write(charArray, 0, offset);
		fileWriter.flush();
		System.out.println(fileName + " successfully stored");
		oWriter.println("File successfully stored");
		oWriter.flush();
	}

}
