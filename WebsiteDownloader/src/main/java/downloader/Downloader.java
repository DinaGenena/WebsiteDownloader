package downloader;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.util.ArrayList;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlImage;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import utils.Connection;
import utils.FileSystemManager;

public class Downloader {

	FileSystemManager fileSystemManager = new FileSystemManager();
	ArrayList<String> traversedLinks = new ArrayList<String>();
	ArrayList<String> links = new ArrayList<String>();
	String baseDir ;
	String baseUrl ;
	String userDir ; 
	int pointer = 0  ;
	
	public Downloader(String baseUrl, String userDir,String baseDir) throws FileSystemException {
		this.baseUrl = baseUrl;
		this.baseDir = baseDir;
		this.userDir = userDir; 
		if ( !fileSystemManager.dirExists(userDir)) {
			throw new FileSystemException(userDir , null , "Directory does not exists");
		}
		fileSystemManager.createDir(baseDir);
	}
	

	public ArrayList<String> getLinks(){
		return links;
	}
	
	public void downloadPage(Connection connection) {
	for (String link : links) {
		HtmlPage page = connection.getPage(link);
		String relDir = link.substring((link.lastIndexOf(".com") + 4));
		createDirs(baseDir.concat(relDir));
		downloadText(page, baseDir.concat(relDir));
		downloadImages(page, baseDir.concat(relDir));
		pointer ++ ;  
	}
}
	
	public void discover(Connection connection , String url) {
	System.out.print("Discovering " + links.size() + " files" +  "\r" );	
	HtmlPage page = connection.getPage(url);
	ArrayList<String> children = getLinks(page);
	if (! children.isEmpty()) {
		String newUrl= new String(); 
		for(String link : children) {
			if (link.startsWith("/")) {
				newUrl = baseUrl.concat(link);
			}
			else {
				if ( traversedLinks.contains("/".concat(newUrl))) {
					continue ; 
				}
				else {
				newUrl = url.concat("/").concat(link);
				}
			}
			 links.add(newUrl);
		     discover(connection,newUrl);
			
		}
	}
}
	
	private void downloadText(HtmlPage page , String dir) {
		String text = page.getBody().asXml();
		fileSystemManager.saveTextFile(text, dir);
	}
	
	private void downloadImages(HtmlPage page,String dir) {
		DomNodeList<DomElement> imageList = page.getElementsByTagName("img"); 
		if (! imageList.isEmpty()){
			imageList.stream()
                     .filter(img -> img.getAttributes().getNamedItem("src") != null)
                     .filter(img -> ! img.getAttributes().getNamedItem("src").getNodeValue().contains("http"))
                     .forEach(img -> {HtmlImage htmlImage = (HtmlImage) img;
                    				try {
                    					htmlImage.saveAs((fileSystemManager.saveImage((img.getAttributes().getNamedItem("src").getNodeValue()), dir)));
                    				} catch (IOException e) {
                    					e.printStackTrace();
                    				} } );
        }
	}
		
	private ArrayList<String> getLinks(HtmlPage page) {
		 ArrayList<String>linkList = new ArrayList<String>(); 
  	 for (HtmlAnchor link : page.getAnchors()) {
  		 String relLink = link.getHrefAttribute();
  		 	if(! (relLink.contains("http")) && ! (relLink.contains("www")) && ! (relLink.contains("#"))  && ! (relLink.contains(":"))) {
  		 		if (!( traversedLinks.contains(relLink))) {
  		 			traversedLinks.add(relLink);
  		 			linkList.add(relLink);
     			 }     			
    		  }
  	 }
  	 linkList.sort(String::compareToIgnoreCase);
  	 return linkList ;	
	}
	
	private void createDirs(String path) {
		fileSystemManager.createDir(path);
	}
	
	public void getProgress() { 
		int place = 0 ; 
		int size = links.size();
		while (place < size) {
		int percentage = (int)((place * 100.0f) / size);
		System.out.print("Downloading " +  percentage + "%"  + "\r");
		 try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		 place = pointer ; 
		}
		System.out.print("Download Complete");
	}


}
