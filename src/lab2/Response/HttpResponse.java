package lab2.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import lab2.Request.*;
import lab2.Header.HttpHeader;

public class HttpResponse {
	
	private final String CRLF = "\r\n";
	private HttpHeader header = null;
	private FileInputStream fileStream = null;
	private File file = null;
	private String root_dir = "FileDirectory";
	private String response = "";
	private String status = "";
	private String entity = "";
	private boolean entityExist = false;
	private boolean imageFileExist = false;
	private boolean fileNotfound = false;
	private boolean file_Permission = true;
	private String filename = "";
	private byte[] data = null;
	
	private boolean fileExist(File file){
		try {
			fileStream = new FileInputStream(file);
			
			if(filename.endsWith(".png") ||
					filename.endsWith(".html") ||
					filename.endsWith(".htm")){
				
				return true;
			} else {
				return false;
			}
		} catch (FileNotFoundException e) {
			e.getMessage();
			fileNotfound = true;
			
			return false;
		} 
	}
	
	private void setUpHeader(String content, long length){
		header = new HttpHeader();

		setResponse(header.getServer(CRLF));
		setResponse(header.getConnection("closed", CRLF));
		setResponse(header.getContentType(content, CRLF));
		setResponse(header.getContentLength(length, CRLF));
		// Indicate end of headers for response
		setResponse(CRLF);
	}

	public HttpResponse(HttpRequest req, byte[] buffer){
		setFilename(req.getFilename());

		System.out.println(filename);
		setFile(filename);

		if(file.isDirectory()){
			if(file.list().length > 0){
				System.out.println("Hello");
				search(file);

				setFile(filename);
			}
		}
		filePermission(file);
		if(fileExist(file) || !file_Permission){
			if(!file_Permission){
				System.out.println("Permission denied " + filename);
				HttpResponsePermissionDenied denied = new HttpResponsePermissionDenied();
				
				doErrorResponse(denied);
			} else {
				try{
					HttpResponseOK ok = new HttpResponseOK();

					setStatus(ok.getSTATUS() + CRLF);
					setUpHeader(contentType(filename), file.length());

					System.out.print("Response: " + response);

					int byteRead = 0;
					if(filename.endsWith(".png")){
						System.out.println("Inside file image");
						data = new byte[(int) file.length()];
						fileStream.read(data);

						imageFileExist = true;
					} else {
						while((byteRead = fileStream.read(buffer)) != -1){
							setResponse(new String(buffer, 0 , byteRead));
						}
					}

					fileStream.close();
				} catch (IOException e){
					System.out.println("I/O issues..");
					e.getMessage();
				} catch (Exception e){
					e.getMessage();
				}
			}
		} else if(fileNotfound) {
			HttpResponseNotOK notOK = new HttpResponseNotOK();
			
			doErrorResponse(notOK);
		} else {
			HttpResponseInternalError internalError 
			= new HttpResponseInternalError();
			
			doErrorResponse(internalError);
		}
	}
	
	private void doErrorResponse(HttpErrorResponse type){
		setStatus(type.getSTATUS() + CRLF);
		setUpEntityBody(type.getEntityBody());
		setUpHeader(contentType(".html"), entity.length());
		entityExist = true;
	}

	private void search(File file) {
		String fileNameToSearch = "index.html";

		for (File temp : file.listFiles()) {
			if (temp.isDirectory()) {
				search(temp);
			} else {
				if (fileNameToSearch.equals(temp.getName().toLowerCase())) {
					String temporary = filename;
					temporary = temporary + "/" + fileNameToSearch;
					setFilename(temporary);
				}
			}
		}	
	}
	
	private void filePermission(File file){
		String restrict = "/Directory2/SubDirectory2/large_network.png";
		String restrict2 = "/" + root_dir;
		
		try{
			System.out.println("Permission " + filename);
			if(filename.equals(restrict) || filename.equals(restrict2)
					|| filename.startsWith(restrict2)){
				file.setExecutable(false);
				file.setReadable(false);
				file.setWritable(false);

				file_Permission = false;
			}
		} catch(Exception e){
			System.err.println("File issues..");
			e.getMessage();
		}
	}

	private String contentType(String file){
		 if(file.endsWith(".htm") || file.endsWith(".html")) {return "text/html";}
		 if(file.endsWith(".png")) {return "image/png";}
		 
		 return "application/outputstream";
	}
	
	private void setFile(String path) {
		if(filename.endsWith("/")){
			setFilename(filename
					.substring(0, (filename.length() - 1)));
		}
		System.out.println("filename: " + filename);
		
		this.file = new File(root_dir + path);
	}
	
	/*
	 * Getters and setters..
	 */
	private void setFilename(String filename) {
		this.filename = filename;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResponse() {
		return response;
	}

	private void setResponse(String response) {
		this.response += response;
	}
	
	private void setUpEntityBody(String entity){
		this.entity = entity;
	}
	
	public String getEntityBody(){
		return this.entity;
	}
	
	public boolean isEntityExist() {
		return entityExist;
	}
	
	public boolean isImageFileExist() {
		return imageFileExist;
	}
	
	public byte[] getBuf() {
		return data;
	}
}
