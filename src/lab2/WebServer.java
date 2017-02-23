package lab2;
/*
 * File:	WebServer.java 
 * Course: 	Computer Network
 * Code: 	IDV701
 * Author: 	Christofer Nguyen
 * Date: 	February, 2017
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
	/*
	 * Main class for server echo functionality and thread handling for multiple
	 * connection of clients.
	 */
	private ServerSocket server = null;
	private Socket connection = null;
	private int myport = 2020;

	// Create instance of server object
	public static void main(String[] args) {
		new WebServer().handleConnection();
	}

	// Method to handle client connection
	public void handleConnection() {
		try {
			checkPortNumber(myport);
			// Create a server socket using the port number specified
			server = new ServerSocket(myport);
			System.out.println("Establishing connection");
			try {
				// Server stays on forever
				while (true) {
					System.out.println("Waiting for connection..");
					connection = server.accept();
					System.out.println("Now connected to " + connection.getInetAddress());
					ClientThread request = new ClientThread(connection);
					Thread thread = new Thread(request);
		
					thread.start();
				}
			} catch (IOException e) {
				e.getMessage();
			} catch (Exception e){
				e.getMessage();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			e.getMessage();
		}
	}

	/*
	 * Method to check port number validity. Number ranging between 0 to 1024
	 * are reserved port numbers for well known services such as HTTP on port
	 * 80.
	 */
	public int checkPortNumber(int port) throws NumberFormatException {
		if (port < 0 || port > 65535 || port < 1024) {
			System.err.println("Invalid port number " + port + " has to be between 1024 - 65535!");
			System.exit(1);
		}
		
		return port;
	}
}