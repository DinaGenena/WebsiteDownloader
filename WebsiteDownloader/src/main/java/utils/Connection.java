package utils;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Connection {

	private static WebClient webClient;
	
	public Connection() {
		 setupConnection();
	}
	
	private static void setupConnection() {
	    webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(false); 
        webClient.getOptions().setCssEnabled(false);
	}

	public HtmlPage getPage(String url) {
		try {
			return webClient.getPage(url);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null; 
	}
	
		public static void closeConnection() {
		webClient.close();
	}
}
