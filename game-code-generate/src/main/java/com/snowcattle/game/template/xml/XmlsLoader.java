package com.snowcattle.game.template.xml;


import com.snowcattle.game.common.util.FileUtil;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class XmlsLoader {
    private XmlsLoader() {
    }

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
			if(!"macros".equals(root.getName())){
				continue;
			}
			for(Element macro : root.getChildren()){
				new MacroObject(macro);
			}
		}

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
			if(!"messages".equals(root.getName())){
				continue;
			}
			for(Element message : root.getChildren()){
				list.add(new MessageObject(message));
			}
		}
	}
}
