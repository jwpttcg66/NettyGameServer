package com.snowcattle.game.template.utils;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils{
	public static void writeToFile(FileContent fileContent) throws IOException{
		File file = new File(fileContent.getFilePath()+fileContent.getFileName());
		if(!file.exists()){
			File folder = file.getParentFile();
			if(!folder.exists()){
				folder.mkdirs();
			}
			file.createNewFile();
		}else{
			file.delete();
			file.createNewFile();
		}
		System.out.println(file.getAbsolutePath());
		FileWriter writer = new FileWriter(file);
		writer.write(fileContent.getContent());
		writer.flush();
		writer.close();
	}
}
