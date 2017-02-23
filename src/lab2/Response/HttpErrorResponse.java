package lab2.Response;

public abstract class HttpErrorResponse {
	protected String status;
	protected String entityBody = null;
	
	abstract String getEntityBody();
	
	protected void setStatus(String newStatus){
		status = newStatus;
	}

	protected String getSTATUS() {
		return status;
	}
}
