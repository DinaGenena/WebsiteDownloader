package downloader;
import java.io.IOException;
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
	
	public void downloadPage(Connection connection, String url, String dir) {
		HtmlPage page = connection.getPage(url);
		createDirs(dir);
		downloadText(page, dir);
		downloadImages(page, dir);
		ArrayList<String> children = getLinks(page);
		if (! children.isEmpty()) {
			for(String link : children) {
				System.out.println(link);
					String newDir = dir.concat(link);
					String newUrl=url.concat(link);
					System.out.println(newUrl);
					//System.out.println(newDir);
					downloadPage(connection,newUrl,newDir);
			}
		}
	}
	
	private void downloadText(HtmlPage page , String dir) {
		// 	String pageName = page.getUrl().getPath();
		//String text =page.asNormalizedText() ; 
		String text = page.getBody().asXml();   ////////////////////////////////
		fileSystemManager.saveTextFile(text, dir);
		

	}
	
	private void downloadImages(HtmlPage page,String dir) {
		DomNodeList<DomElement> imageList = page.getElementsByTagName("img"); 
		if (! imageList.isEmpty()){
		for (DomElement  img : imageList) {
        	      
			if ((img.getAttributes().getNamedItem("src") != null) ) {
				if (! img.getAttributes().getNamedItem("src").getNodeValue().contains("http")) {
			String imgName = (img.getAttributes().getNamedItem("src").getNodeValue());
//			System.out.println("IMAGENAME: " + imgName);
            HtmlImage htmlImage = (HtmlImage) img;
			try {
				htmlImage.saveAs((fileSystemManager.saveImage(imgName, dir)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
        		}
		}
	}
		}
	}
	
	private ArrayList<String> getLinks(HtmlPage page) {
		 ArrayList<String>linkList = new ArrayList<String>(); 
    	 for (HtmlAnchor link : page.getAnchors()) {
    		 String relLink = link.getHrefAttribute();
    		 	if(relLink.startsWith("/") && ! (relLink.contains("#"))) {
    		 		if (!( traversedLinks.contains(relLink))) {
    		 			traversedLinks.add(relLink);
    		 			linkList.add(relLink);
       			 }
       			//new File("C:\\Users\\Dina\\Desktop\\images/" + n.getHrefAttribute()).mkdirs();     			
      		  }
    	 }
    	 return linkList ; 	
	}
	
	private void createDirs(String path) {
		fileSystemManager.createDir(path);
	}

}
