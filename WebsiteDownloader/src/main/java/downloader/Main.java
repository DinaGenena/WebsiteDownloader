package downloader;

import java.nio.file.FileSystemException;
import java.util.Scanner;

import utils.Connection;

public class Main {

	public static void main(String[] args) throws FileSystemException {
		// TODO Auto-generated method stub

		Scanner sc=new Scanner(System.in);  
		System.out.println("Enter directory to save");  
		String userDir = ((sc.next()).strip());
		String baseDir = userDir.concat("\\tretton37");
		String baseUrl = "https://tretton37.com" ;
		Connection connection1 = new Connection();
		connection1.setupConnection();
		Connection connection2 = new Connection();
		connection2.setupConnection();
		Downloader downloader = new Downloader(baseUrl,userDir,baseDir);
		downloader.discover(connection1,baseUrl);
		connection1.closeConnection();
	    new Thread(() -> downloader.getProgress()).start();	
	    new Thread(() -> downloader.downloadPage(connection2)).start();
		connection2.closeConnection();
		
	}
}
