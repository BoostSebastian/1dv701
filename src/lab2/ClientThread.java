package lab2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

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
			if ((byteRead = inputStream.read(buf)) != -1) {
				method_request += new String(buf, 0, byteRead);
				System.out.println(method_request);
			}

			System.out.println();
			System.out.println("Recieved message from client: " + method_request);

			HttpRequest request = new HttpRequest(method_request);
			HttpResponse response = new HttpResponse(request, buf);
			
			System.out.println(response.isEntityExist());
			System.out.println(response.isImageFileExist());
			if(response.isEntityExist()){
				outputStream.write(response.getEntityBody().getBytes());
			}
			
			if(response.isImageFileExist()){
				outputStream.write(response.getBuf());
			}
			
			outputStream.write(response.getResponse().getBytes());
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
		} catch (IOException e) {
			System.err.println("Issue with closing down..");
			e.printStackTrace();
		}
	}
}
