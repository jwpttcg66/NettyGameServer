package com.snowcattle.game.template.xml;


import com.snowcattle.game.common.util.FileUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class XmlsLoader {
	public static void loadMacro(String folderPath) throws JDOMException, IOException{

		File file =  FileUtil.getFile(folderPath);
//		File file = new File(url.getFile());
		if(!file.exists() || file.isFile()){
			return;
		}
		folderPath = file.getPath();
		String[] xmlFileNames = file.list();
		for(String xmlFileName : xmlFileNames){
			Document doc = new SAXBuilder().build(new File(folderPath,xmlFileName));
			Element root = doc.getRootElement();
			if(!root.getName().equals("macros")){
				continue;
			}
			for(Element macro : root.getChildren()){
				new MacroObject(macro);
			}
		}
		
		return;
	}
	
	public static void loadFormat(String folderPath, List<MessageObject> list) throws JDOMException, IOException{

		File file =  FileUtil.getFile(folderPath);
		if(!file.exists() || file.isFile()){
			return ;
		}
		folderPath = file.getPath();
		String[] xmlFileNames = file.list();
		for(String xmlFileName : xmlFileNames){
			Document doc = new SAXBuilder().build(new File(folderPath, xmlFileName));
			Element root = doc.getRootElement();
			if(!root.getName().equals("messages")){
				continue;
			}
			for(Element message : root.getChildren()){
				list.add(new MessageObject(message));
			}
		}
	}
}
