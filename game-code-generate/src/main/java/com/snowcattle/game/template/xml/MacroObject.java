package com.snowcattle.game.template.xml;


import org.jdom2.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MacroObject {
	private static Map<String, MacroObject> allMacros = new HashMap<String, MacroObject>();
	
	private String className;
	private String namespace;
	private String comment;
	private String vm;
	private List<FieldObject> fieldList = new ArrayList<FieldObject>();
	
	public MacroObject(){}
	public MacroObject(Element element){
		this.className = element.getAttributeValue("name");
		this.namespace = element.getAttributeValue("javaPackage");
		this.comment = element.getAttributeValue("comment");
		this.vm = element.getAttributeValue("vm");
		for(Element child : element.getChildren()){
			fieldList.add(new FieldObject(child));
		}
		
		allMacros.put(this.className, this);
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<FieldObject> getFieldList() {
		return fieldList;
	}
	public void setFieldList(List<FieldObject> fieldList) {
		this.fieldList = fieldList;
	}
	public static Map<String, MacroObject> getAllMacros() {
		return allMacros;
	}
	public static void setAllMacros(Map<String, MacroObject> allMacros) {
		MacroObject.allMacros = allMacros;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getVm() {
		return vm;
	}
	public void setVm(String vm) {
		this.vm = vm;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getPackPath(){
		return this.namespace.replace('.', '/');
	}

	public String getOutputFileName(){
		return this.className +".java";
	}

	public String getVmFileName(){
		return this.vm+ ".vm";
	}
}
