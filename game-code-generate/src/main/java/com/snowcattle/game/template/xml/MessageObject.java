package com.snowcattle.game.template.xml;


import org.jdom2.Element;
import java.util.ArrayList;
import java.util.List;

public class MessageObject {
	private String comment;
	private String className;
	private String namespace;
	private String cmdType;
	private String vm;
	private List<FieldObject> fieldList = new ArrayList<FieldObject>();
	private String importPackage;
	public MessageObject(){}

	public MessageObject(Element element){
		this.comment = element.getAttributeValue("comment");

		this.namespace = element.getAttributeValue("javaPackage");
		this.className = element.getAttributeValue("name");

		this.importPackage = element.getAttributeValue("importPackage");
		this.cmdType = element.getAttributeValue("cmdType");
		this.vm = element.getAttributeValue("vm");
		for(Element child : element.getChildren()){
			fieldList.add(new FieldObject(child));
		}
	}

	
	public String getVmFileName(){
		return this.vm+ ".vm";
	}
	
	public String getOutputFileName(){
		return this.className +".java";
	}
	
	public String getPackPath(){
		return this.namespace.replace('.', '/');
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
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
	public List<FieldObject> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<FieldObject> fieldList) {
		this.fieldList = fieldList;
	}

	public String getImportPackage() {
		return importPackage;
	}

	public void setImportPackage(String importPackage) {
		this.importPackage = importPackage;
	}

	public String getCmdType() {
		return cmdType;
	}

	public void setCmdType(String cmdType) {
		this.cmdType = cmdType;
	}
}
