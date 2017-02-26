/*
 * File:	ClientThread.java 
 * Course: 	Computer Network
 * Code: 	IDV701
 * Author: 	Christofer Nguyen & Jonathan Walkden
 * Date: 	February, 2017
 */

package lab2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import lab2.Request.HttpRequest;
import lab2.Response.HttpResponse;

public class ClientThread implements Runnable {

	private Socket connection = null;
	private DataInputStream inputStream = null;
	private DataOutputStream outputStream = null;
	private byte[] buf = null;
	private int bufferSize = 1024;

	public ClientThread(Socket client) {
		buf = new byte[bufferSize];
		connection = client;

		try {
			// Send and receive
			inputStream = new DataInputStream(connection.getInputStream());
			outputStream = new DataOutputStream(connection.getOutputStream());

		} catch (IOException e) {
			System.err.println("Problem with the stream!");
			e.printStackTrace();
		} catch (Exception e) {
			e.getMessage();
		}
	}

	@Override
	public void run() {
		String method_request = "";
		try {
			// Checks if there is anything to read from the buffer
			int byteRead = 0;
			
			// append information read from inputStream into a useful string that contains information about the request
			if ((byteRead = inputStream.read(buf)) != -1) {
				method_request += new String(buf, 0, byteRead);
			}

			System.out.println();
			System.out.println("Recieved message from client: " + method_request);
			
			// create new instance of HttpRequest in order to get requested file name
			HttpRequest request = new HttpRequest(method_request);
			
			// new instance of HttpResponse in order to execute on the request and return appropriate information and responses.
			HttpResponse response = new HttpResponse(request, buf);
			
			// writes out startline to the client
			outputStream.write(response.getStatus().getBytes());
			// writes out headers to the client
			outputStream.write(response.getResponse().getBytes());
			
			// writes out a html entity body for error responses
			if(response.isEntityExist()){
				outputStream.write(response.getEntityBody().getBytes());
			}
			
			// writes out png files as raw bytes
			if(response.isImageFileExist()){
				outputStream.write(response.getBuf());
			}
			// flushes out remaining bytes, if there is any
			outputStream.flush();
			
		} catch (IOException e) {
			System.out.println("Problem with the stream!");
			e.printStackTrace();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			closeDown();
		}
	}

	private void closeDown() {
		try {
			inputStream.close();
			outputStream.close();
			connection.close();
			System.out.println("Client connection ended..");
		} catch (IOException e) {
			System.err.println("Issue with closing down..");
			e.printStackTrace();
		}
	}
}
