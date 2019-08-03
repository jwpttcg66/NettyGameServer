package com.snowcattle.game.template;


import com.snowcattle.game.template.utils.FileConentFactory;
import com.snowcattle.game.template.utils.FileContent;
import com.snowcattle.game.template.utils.FileContentFactoryImpl;
import com.snowcattle.game.template.utils.FileUtils;
import com.snowcattle.game.template.xml.MacroObject;
import com.snowcattle.game.template.xml.MessageObject;
import com.snowcattle.game.template.xml.XmlsLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;



public final class MessageGenerator {

	private static final String xmlPath = "config/model/";
	private static final String vmPath = "config/template/";
	private static final String encode = "UTF-8";

	private static final String outputProjectPath = "game-code-generate/src/main/java/";

	private static final FileConentFactory fileConentFactory = new FileContentFactoryImpl();

	public static List<MacroObject> formats = new ArrayList<>();

    private MessageGenerator() {
    }

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
            FileContent fileContent = fileConentFactory.create(msgObj.getOutputFileName(), converter.convert(msgObj),
                                                               outputProjectPath + msgObj.getPackPath() + '/');
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
            FileContent fileContent = fileConentFactory.create(msgObj.getOutputFileName(), converter.convert(msgObj),
                                                               outputProjectPath + msgObj.getPackPath() + '/');
			try {
				FileUtils.writeToFile(fileContent);
				System.out.println("已生成"+msgObj.getOutputFileName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
