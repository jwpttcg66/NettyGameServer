package com.snowcattle.game.template.xml;


import org.jdom2.Attribute;
import org.jdom2.Element;

public class FieldObject {
	private String type;
	private String name;
	private String comment;
	private String generic;
	private MacroObject genericObj = null;
	private MacroObject selfObj = null;
	
	public FieldObject(){}
	public FieldObject(Element element){
		this.type = element.getAttributeValue("type");
		this.name = element.getAttributeValue("name");
		this.comment =element.getAttributeValue("comment");
		
		Attribute genericAttr = element.getAttribute("generic");
		if(genericAttr != null){
			this.generic = genericAttr.getValue();
		}
		
		MacroObject macroObj = MacroObject.getAllMacros().get(this.type);
		if(macroObj != null){
			this.selfObj = macroObj;
		}
		macroObj = MacroObject.getAllMacros().get(this.generic);
		if(macroObj != null){
			this.genericObj = macroObj;
		}
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getGeneric() {
		return generic;
	}
	public void setGeneric(String generic) {
		this.generic = generic;
	}
	public MacroObject getGenericObj() {
		return genericObj;
	}
	public void setGenericObj(MacroObject genericObj) {
		this.genericObj = genericObj;
	}
	public MacroObject getSelfObj() {
		return selfObj;
	}
	public void setSelfObj(MacroObject selfObj) {
		this.selfObj = selfObj;
	}
}
