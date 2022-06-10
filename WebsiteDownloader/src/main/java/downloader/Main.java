package downloader;

import utils.Connection;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection connection = new Connection();
		connection.setupConnection();
		Downloader downloader = new Downloader();
		downloader.downloadPage(connection, "https://tretton37.com", "C:\\Users\\Dina\\Desktop\\tretton37/");
		connection.closeConnection();
		
	}

}
