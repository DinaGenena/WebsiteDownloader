package org.WebsiteDownloader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystemException;
import downloader.Downloader;
import utils.Connection;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DownloaderTest {
    ClassLoader classLoader = getClass().getClassLoader();
    File file = new File(classLoader.getResource("test.html").getFile());
    Downloader downloader;
    Connection connection = new Connection() ; 
	
	
@Before
   public void setUp() {
	 String htmlSample="<!DOCTYPE html>\r\n" + 
			"<head>\r\n" + 
			"<title>A Sample HTML Document (Test File)</title>\r\n" + 
			"<meta charset=\"utf-8\">\r\n" + 
			"<meta name=\"description\" content=\"A sample HTML document for testing purposes.\">\r\n" + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
			"<body>\r\n" + 
			" \r\n" + 
			"<h1>A Sample HTML Document (Test File)</h1>\r\n" + 
			"<p>A sample HTML document for testing purposes.</p>\r\n" + 
			"<p><a href=\"http://google.com\">Go to external website</a></p>\r\n" + 
			"<p><a href=\"mailto:@\">Go to invalid link</a></p>\r\n" + 
			" <img src=\"/assets/img.jpg\">\r\n"  + 
			"\r\n" + 
			"</body>\r\n" + 
			"</html>"; 
	
	    BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(file));
		    writer.write(htmlSample);
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
   }

@Test
   public void testDiscoverInvalidLinks() {
	String dir = null ;
	try {
		dir = file.toURI().toURL().toString();
	} catch (MalformedURLException e1) {
		e1.printStackTrace();
	}
      try {
		downloader = new Downloader(dir,file.getParent(),file.getAbsolutePath());
	} catch (FileSystemException e) {
		e.printStackTrace();
	}
      downloader.discover(connection, dir);
      assertTrue (downloader.getLinks().isEmpty());
   }

@After
public void cleanUp() {
	connection.closeConnection();
	}
}
