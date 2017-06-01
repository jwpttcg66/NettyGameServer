package com.snowcattle.game.common.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author ZERO
 *
 */
public class CodecUtil {
	/**
	 * 反序列化
	 * @param <T>
	 * @param bytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T decode(byte[] bytes){
		if(bytes==null||bytes.length==0) return null;
		ObjectInputStream objIn = null;
		try {
			objIn = new ObjectInputStream(new ByteArrayInputStream(bytes));
			Object object = objIn.readObject();
			return (T) object;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			if(objIn!=null){
				try {
					objIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	/**
	 * 序列化
	 * @param <T>
	 * @param object
	 * @return
	 */
	public static <T> byte[] encode(T object){
		if(object==null) return null;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream objectOut = null;
		try {
			objectOut = new ObjectOutputStream(out);
			objectOut.writeObject(object);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(objectOut!=null){
				try {
					objectOut.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return out.toByteArray();
	}
}
