/*
 * File:	HttpResponsePermissionDenied.java 
 * Course: 	Computer Network
 * Code: 	IDV701
 * Author: 	Christofer Nguyen & Jonathan Walkden
 * Date: 	February, 2017
 */


package lab2.Response;

public class HttpResponsePermissionDenied extends HttpErrorResponse {

	public HttpResponsePermissionDenied(){
		setStatus("HTTP/1.1 403 Permission Denied ");
	}

	@Override
	String getEntityBody() {
		entityBody = "<HTML>" +
				  "<HEAD><TITLE>403 Permission Denied</TITLE></HEAD>" +
				  "<BODY>Permission denied!</BODY></HTML>";
		
		return entityBody;
	}	
}
