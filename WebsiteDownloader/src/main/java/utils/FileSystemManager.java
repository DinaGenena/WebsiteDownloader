package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class FileSystemManager {
	

	public void saveTextFile(String text, String pageName, String dir) {
		File textFile = new File(dir.concat(pageName).concat(".txt"));
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
	
	public File saveImage(String imageName, String dir) {
			return new File(dir.concat((imageName.substring(imageName.lastIndexOf("/") + 1)))) ;
	}
}
