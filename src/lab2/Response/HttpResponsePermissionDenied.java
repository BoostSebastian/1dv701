package lab2.Response;

public class HttpResponsePermissionDenied {
	
	private final String STATUS = "HTTP/1.1 403 Permission Denied ";
	private String entityBody = null;
	
	public String getEntityBody(){
		entityBody = "<HTML>" +
				  "<HEAD><TITLE>403 Permission Denied</TITLE></HEAD>" +
				  "<BODY>Permission denied!</BODY></HTML>";
		
		return entityBody;
	}

	public String getSTATUS() {
		return STATUS;
	}	
}
