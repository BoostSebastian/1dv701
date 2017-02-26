/*
 * File:	HttpResponseInternalError.java 
 * Course: 	Computer Network
 * Code: 	IDV701
 * Author: 	Christofer Nguyen & Jonathan Walkden
 * Date: 	February, 2017
 */

package lab2.Response;

public class HttpResponseInternalError extends HttpErrorResponse {
	
	public HttpResponseInternalError(){
		setStatus("HTTP/1.1 500 Internal Server Error ");
	}

	@Override
	String getEntityBody() {
		entityBody = "<HTML>" +
				  "<HEAD><TITLE>500 Internal Server Error</TITLE></HEAD>" +
				  "<BODY>The request was not completed. "
				  + "The server met an unexpected condition.</BODY></HTML>";
		
		return entityBody;
	}

}
