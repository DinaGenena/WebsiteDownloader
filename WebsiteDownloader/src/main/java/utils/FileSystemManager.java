package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSystemManager {
	

	public void saveTextFile(String text, String dir) {
		dir = dir.replaceAll(" ", "_");
	    String pageName = dir.substring(dir.lastIndexOf("/") ); 
	    if (pageName.equals("/")) {pageName = "/home" ; }
		File textFile = new File(dir.concat(pageName).concat(".html"));
	    Writer writer;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(textFile)));
		    writer.write(text);
		    writer.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}
	
	public File saveHtml(String pageName , String dir) {
		return new File(dir.concat(pageName)) ;
	}
	
	public File saveImage(String imageName, String dir) {
		imageName = imageName.replaceAll(" ", "_");
		return new File(dir.concat((imageName.substring(imageName.lastIndexOf("/"))))) ;
	}
	
	public void createDir(String dir) {
		String dirName ; 
		if (dir.contains(".com")){
			dirName = dir.substring(dir.lastIndexOf(".com") + 5);
		}
		else {
			dirName = dir ; 
		}
		new File(dirName).mkdirs();
	}
		
	public boolean dirExists(String dir) {
		if (Files.isDirectory(Paths.get(dir))) {
			return true;
		}
		return false;
	}
	
}
