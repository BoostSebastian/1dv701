/*
 * File:	HttpRequest.java 
 * Course: 	Computer Network
 * Code: 	IDV701
 * Author: 	Christofer Nguyen & Jonathan Walkden
 * Date: 	February, 2017
 */

package lab2.Request;

public class HttpRequest {

	private String filename;
	private String requestMethod;

	public HttpRequest(String method) {
		String[] temp = method.split("\n");
		
		//parses the string arguement to find the request method type (get, post)
		setRequestMethod(temp[0].split(" ")[0]);
		
		// parses the string arguement to find the name of the file/directory being requested
		setFilename(temp[0].split(" ")[1]);
	}

	private void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getFilename() {
		return filename;
	}
	
	public String getRequestMethod() {
		return requestMethod;
	}
	
	private void setFilename(String filename) {
		this.filename = filename;
	}
}