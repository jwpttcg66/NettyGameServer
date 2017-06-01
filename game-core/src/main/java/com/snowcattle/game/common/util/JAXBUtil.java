package com.snowcattle.game.common.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * 使用JAXB操作XML的工具类
 *
 *
 */
public class JAXBUtil {
	public static void write(Object xmlObject, Writer writer, boolean fragment) {
		JAXBContext _context = null;
		try {
			_context = JAXBContext.newInstance(xmlObject.getClass());
			Marshaller _marshaller = _context.createMarshaller();
			_marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			_marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			_marshaller.setProperty(Marshaller.JAXB_FRAGMENT, fragment);
			_marshaller.marshal(xmlObject, writer);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T read(Class<T> xmlObjectClass, String xmlPath) {
		JAXBContext _context = null;
		Reader reader = null;
		try {
			reader = new FileReader(xmlPath);
			_context = JAXBContext.newInstance(xmlObjectClass);
			Unmarshaller _unmarshaller = _context.createUnmarshaller();
			return (T) _unmarshaller.unmarshal(reader);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
			}
		}
	}
}
