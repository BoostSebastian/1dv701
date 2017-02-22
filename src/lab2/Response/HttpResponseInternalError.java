package lab2.Response;

public class HttpResponseInternalError {
	
	private final String STATUS = "HTTP/1.1 500 Internal Server Error ";
	private String entityBody = null;
	
	public String getEntityBody(){
		entityBody = "<HTML>" +
				  "<HEAD><TITLE>500 Internal Server Error</TITLE></HEAD>" +
				  "<BODY>The request was not completed. "
				  + "The server met an unexpected condition.</BODY></HTML>";
		
		return entityBody;
	}

	public String getSTATUS() {
		return STATUS;
	}
}
