package lab2.Header;

public class HttpHeader {
	
	public String getServer(String CRLF){
		return "Server: My local web server " + CRLF;
	}
	
	public String getConnection(String connection, String CRLF){
		return "Connection: " + connection + " " + CRLF;
	}
	
	public String getContentType(String type, String CRLF){
		return "Content-Type: " + type + " " + CRLF;
	}
	
	public String getContentLength(long length, String CRLF){
		return "Content-Length: " + length + " " + CRLF;
	}

}
