/*
 * File:	HttpResponseOK.java 
 * Course: 	Computer Network
 * Code: 	IDV701
 * Author: 	Christofer Nguyen & Jonathan Walkden
 * Date: 	February, 2017
 */

package lab2.Response;

public class HttpResponseOK {

	private final String STATUS = "HTTP/1.1 200 OK ";

	public String getSTATUS() {
		return STATUS;
	}
}
