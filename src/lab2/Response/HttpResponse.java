/*
 * File:	HttpResponse.java 
 * Course: 	Computer Network
 * Code: 	IDV701
 * Author: 	Christofer Nguyen & Jonathan Walkden
 * Date: 	February, 2017
 */

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

	public HttpResponse(HttpRequest req, byte[] buffer){
		
		// sets file name based on HttpeRequest argument 
		setFilename(req.getFilename());
		
		// setFile method which sets file path
		setFile(filename);

		// check to see if requested path file is a directory
		if(file.isDirectory()){
			if(file.list().length > 0){		
				// search method which looks for index.html file in directory
				search(file);
				setFile(filename);
			}
		}
		
		// check if file has permission to be accessed.
		filePermission(file);
		if(fileExist(file) || !file_Permission){	
			// if persmission is denied then send denied response 403
			if(!file_Permission){
				System.out.println("Permission denied: " + filename);
				HttpResponsePermissionDenied denied = new HttpResponsePermissionDenied();
				
				doErrorResponse(denied);
			} else {
				try{
					HttpResponseOK ok = new HttpResponseOK();

					setStatus(ok.getSTATUS() + CRLF);
					setUpHeader(contentType(filename), file.length());
					
					// print out response headers for additional information
					System.out.print("\nResponse: \n" + response);

					int byteRead = 0;
					
					// if file is a png image load image data and display
					if(filename.endsWith(".png")){
						data = new byte[(int) file.length()];
						fileStream.read(data);

						imageFileExist = true;
					} 
					// else load content to be displayed 
					else {
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
		} 
		
		// 404 response if file cannot be found in directory
		else if(fileNotfound) {
			HttpResponseNotOK notOK = new HttpResponseNotOK();
			
			doErrorResponse(notOK);
		} 
		
		// 500 response if there is an error with file request that is not handled eg. wrong format
		else {
			HttpResponseInternalError internalError 
			= new HttpResponseInternalError();
			
			doErrorResponse(internalError);
		}
	}
	
	// check to see if file exists with allowed extension
	private boolean fileExist(File file){
		try {
			fileStream = new FileInputStream(file);
			
			// does file have a valid extension
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
	
	// set up response headers with appropriate information
	private void setUpHeader(String content, long length){
		header = new HttpHeader();

		setResponse(header.getServer(CRLF));
		setResponse(header.getConnection("closed", CRLF));
		setResponse(header.getContentType(content, CRLF));
		setResponse(header.getContentLength(length, CRLF));
		// Indicate end of headers for response
		setResponse(CRLF);
	}
	
	// helper method which sets chosen response based on arguement
	private void doErrorResponse(HttpErrorResponse type){
		setStatus(type.getSTATUS() + CRLF);
		setUpEntityBody(type.getEntityBody());
		setUpHeader(contentType(".html"), entity.length());
		entityExist = true;
	}

	/* 
	 * method which checks if directory has an index.html file in it so that it can be 
	 * displayed as a default when no specific file is specified in directory
	 */
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
	
	// method to check if file has permission to be accessed via the request.
	private void filePermission(File file){
		
		// manually set restrictions on which files can be accessed.
		String restrict = "/Directory2/SubDirectory2/large_network.png";
		String restrict2 = "/" + root_dir;
		
		try{
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
	
	
	/* 
	 * method which removes "/" from filename and appends "l" the "htm" so that it is not necessary when 
	 * requesting to be too specific and then sets file path to requested file.
	 */
	private void setFile(String path) {
		if(path.endsWith("/")){
			setFilename(path.substring(0, (path.length() - 1)));
		}
		
		if(filename.endsWith(".htm")){
			setFilename(filename + "l");
		}
		
		System.out.println("filename: " + filename);
		this.file = new File(root_dir + filename);
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
