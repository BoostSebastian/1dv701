package lab2.Response;

public class HttpResponseNotOK {

	private final String STATUS = "HTTP/1.1 404 NOT FOUND ";
	private String entityBody = null;
	
	public String getEntityBody(){
		entityBody = "<HTML>" +
				  "<HEAD><TITLE>404 Not Found</TITLE></HEAD>" +
				  "<BODY>Requested page is not found!</BODY></HTML>";
		
		return entityBody;
	}

	public String getSTATUS() {
		return STATUS;
	}
}
