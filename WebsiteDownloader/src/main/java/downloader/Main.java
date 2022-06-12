package downloader;

import java.util.Collections;

import utils.Connection;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection connection1 = new Connection();
		connection1.setupConnection();
		Connection connection2 = new Connection();
		connection2.setupConnection();
		Downloader downloader = new Downloader("https://tretton37.com", "C:\\Users\\Dina\\Desktop\\tretton37/");
		downloader.discover(connection1, "https://tretton37.com");
		connection1.closeConnection();;
	   // new Thread(() -> downloader.getProgress()).start();	
	    new Thread(() -> downloader.downloadPage(connection2)).start();
		connection2.closeConnection();
		
	}
}
