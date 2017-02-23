package lab2.Response;

public class HttpResponseNotOK extends HttpErrorResponse{

	public HttpResponseNotOK(){
		setStatus("HTTP/1.1 404 NOT FOUND ");
	}

	@Override
	String getEntityBody() {
		entityBody = "<HTML>" +
				  "<HEAD><TITLE>404 Not Found</TITLE></HEAD>" +
				  "<BODY>Requested page is not found!</BODY></HTML>";
		
		return entityBody;
	}
}
