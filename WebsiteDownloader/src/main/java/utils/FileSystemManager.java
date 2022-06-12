package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
		String dirName = dir.substring(dir.lastIndexOf(".com\\") + 1);
		new File(dirName).mkdirs();
	}
	
	public int localFileCount(String dir) { 
	    try {
	    	Path path = Paths.get(dir) ; 
			return (int)Files.walk(path)
			            .parallel()
			            .filter(p -> p.toFile().isDirectory())
			            .count();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 1;
	  }
	
	
	public boolean dirExists(String dir) {
		if (Files.isDirectory(Paths.get(dir))) {
			return true;
		}
		return false;
	}
	
}
