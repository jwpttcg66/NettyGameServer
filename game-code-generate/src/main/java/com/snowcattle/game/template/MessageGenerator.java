package com.snowcattle.game.template;


import com.snowcattle.game.template.utils.FileContent;
import com.snowcattle.game.template.utils.FileUtils;
import com.snowcattle.game.template.xml.MacroObject;
import com.snowcattle.game.template.xml.MessageObject;
import com.snowcattle.game.template.xml.XmlsLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;



public class MessageGenerator {

	private static String xmlPath = "config/model/";
	private static String vmPath = "config/template/";
	private static String encode = "UTF-8";
	
	private static String outputProjectPath = "game-code-template-generate/src/main/java/";

	public static List<MacroObject> formats = new ArrayList<>();
	public static void main(String[] args) {
		generateMacroObject();
		generateTemplate();
	}

	public static void generateMacroObject(){
		try {
			XmlsLoader.loadMacro(xmlPath);
//			formats = XmlsLoader.loadFormat(xmlPath);
			for(Entry<String, MacroObject> entry: MacroObject.getAllMacros().entrySet()){
				formats.add(entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		Converter converter = new Converter(vmPath, encode);
		for(MacroObject msgObj : formats){
			FileContent fileContent = new FileContent();
			fileContent.setContent(converter.convert(msgObj));
			fileContent.setFileName(msgObj.getOutputFileName());
			fileContent.setFilePath(outputProjectPath + msgObj.getPackPath() + "/");
			try {
				FileUtils.writeToFile(fileContent);
				System.out.println("已生成"+msgObj.getOutputFileName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void generateTemplate(){
		List<MessageObject> templateformats = new ArrayList<>();
		try {
			XmlsLoader.loadFormat(xmlPath, templateformats);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Converter converter = new Converter(vmPath, encode);
		for(MessageObject msgObj : templateformats){
			FileContent fileContent = new FileContent();
			fileContent.setContent(converter.convert(msgObj));
			fileContent.setFileName(msgObj.getOutputFileName());
			fileContent.setFilePath(outputProjectPath + msgObj.getPackPath() + "/");
			try {
				FileUtils.writeToFile(fileContent);
				System.out.println("已生成"+msgObj.getOutputFileName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
