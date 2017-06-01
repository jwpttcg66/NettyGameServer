package com.snowcattle.game.common.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import java.net.URL;


/**
 *
 *
 */
public class JdomUtils {
	public static final String ARRAY_SEPARATOR = ";";

	public static Element getRootElemet(String xmlPath) {
		SAXBuilder builder = new SAXBuilder();
//		builder.setValidation(false);
		Document doc = null;
		try {
			doc = builder.build(xmlPath);
			return doc.getRootElement();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Element getRootElemet(URL xmlPath) {
		SAXBuilder builder = new SAXBuilder();
//		builder.setValidation(false);
		Document doc = null;
		try {
			doc = builder.build(xmlPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc.getRootElement();
	}

	public static int getIntAttriValue(Element e, String attrName) {
		String attributeValue = e.getAttributeValue(attrName);
		if (attributeValue == null) {
			return -1;
		}
		return Integer.valueOf(attributeValue);
	}

	public static short getShortAttriValue(Element e, String attrName) {
		String attributeValue = e.getAttributeValue(attrName);
		if (attributeValue == null) {
			return -1;
		}
		return Short.valueOf(attributeValue);
	}

	public static byte getByteAttriValue(Element e, String attrName) {
		String attributeValue = e.getAttributeValue(attrName);
		if (attributeValue == null) {
			return -1;
		}
		return Byte.valueOf(attributeValue);
	}

	public static int[] getIntArrayAttriValue(Element e, String attrName) {
		String[] temp = e.getAttributeValue(attrName).split(ARRAY_SEPARATOR);
		int[] result = new int[temp.length];
		for (int i = 0; i < temp.length; i++) {
			result[i] = Integer.valueOf(temp[i]);
		}
		return result;
	}

	public static short[] getShortArrayAttriValue(Element e, String attrName) {
		String[] temp = e.getAttributeValue(attrName).split(ARRAY_SEPARATOR);
		short[] result = new short[temp.length];
		for (int i = 0; i < temp.length; i++) {
			result[i] = Short.valueOf(temp[i]);
		}
		return result;
	}
}
