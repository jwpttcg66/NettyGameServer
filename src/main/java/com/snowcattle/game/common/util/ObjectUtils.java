package com.snowcattle.game.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author ZERO
 *
 */
public class ObjectUtils {
	
	/**
	 * 截取某列表的部分数据
	 * @param <T>
	 * @param list
	 * @param skip
	 * @param pageSize
	 */
	public static <T> List<T> getSubListPage(List<T> list, int skip, int pageSize) {
		if(list==null||list.isEmpty()){
			return null;
		}
		int startIndex = skip;
		int endIndex = skip+pageSize;
		if(startIndex>endIndex||startIndex>list.size()){
			return null;
		}
		if(endIndex>list.size()){
			endIndex = list.size();
		}
		return list.subList(startIndex, endIndex);
	}
	
	/**
	 * 通过远程URL获取本地IP地址
	 * @param urlCanGainIp
	 */
	public static String getInetIpAddress(String urlCanGainIp){
		InputStream in = null;
		try {
			URL url = new URL(urlCanGainIp);
			in = url.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			String line = "",ip=null;
			while((line=reader.readLine())!=null){
				ip = parseIpAddress(line);
				if(!StringUtils.isEmpty(ip)){
					return ip;
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	/**
	 * 判断某个地址是否是IP地址
	 * @param content
	 */
	public static boolean isIpAddress(String content){
		String rt = parseIpAddress(content);
		if(!StringUtils.isEmpty(rt)){
			if(rt.equals(content)){
				return true;
			}
		}
		return false;
	}
	/*
	 * 解释IP地址
	 */
	private static String parseIpAddress(String content){
		String regexIp = "((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]\\d|\\d)\\.){3}(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]\\d|[1-9])";
		Pattern pattern = Pattern.compile(regexIp);
		Matcher matcher = pattern.matcher(content);
		String rt = null;
		while(matcher.find()){
			rt = matcher.group();
		}
		return rt;
	}
	/**
	 * 获取某个对象某些字段的Map
	 * @param obj
	 */
	public static Map<String,String> getMap(Object obj,String ...strings){
		Map<String,String> map = new HashMap<String, String>();
		boolean addAllFields = false;
		if(strings==null||strings.length==0){
			addAllFields=true;
		}
		if(obj!=null){
			Field[] fields = getAllFields(obj);
			for(Field field:fields){
				field.setAccessible(true);
				try {
					boolean needsAddToMap = false;
					for(String s:strings){
						if(field.getName().equals(s)){
							needsAddToMap=true;
							break;
						}
					}
					if(needsAddToMap||addAllFields){
						map.put(field.getName(), getFieldsValueStr(obj,field.getName()).toString());
					}
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(!addAllFields&&strings.length!=map.size()){
			return new HashMap<String, String>();
		}
		return map;
	}
	private static Field[] getAllFields(Object obj){
		Class<?> clazz = obj.getClass();
		Field[] rt=null;
		for(;clazz!=Object.class;clazz=clazz.getSuperclass()){
			Field[] tmp = clazz.getDeclaredFields();
			rt = combine(rt, tmp);
		}
		return rt;
	}
	private static Field[] combine(Field[] a, Field[] b){
		if(a==null){
			return b;
		}
		if(b==null){
			return a;
		}
		Field[] rt = new Field[a.length+b.length];
		System.arraycopy(a, 0, rt, 0, a.length);
		System.arraycopy(b, 0, rt, a.length, b.length);
		return rt;
	}
	private static Object getFieldsValueObj(Object obj,String fieldName){
		Field field = getDeclaredField(obj,fieldName);
		field.setAccessible(true);
		try {
			return field.get(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getFieldsValueStr(Object obj,String fieldName){
		Object o = ObjectUtils.getFieldsValueObj(obj, fieldName);
		if(o instanceof Date){
			return TimeUtils.dateToString((Date)o);
		}
		return o.toString();
	}
	
//	public static String getFieldsValueStr(Object obj,String[] fieldName){
//		Map<String,String> keyMap=ObjectUtils.getMap(obj, fieldName);
//		StringBuilder sb=new StringBuilder();
//		for(String keyName:fieldName){
//			sb.append(keyMap.get(keyName)).append("#");
//		}
//		return sb.toString();
//	}
	
	
	public static String getFieldsValueStr(Object obj,String[] fieldName){
		Map<String,String> keyMap=ObjectUtils.getMap(obj, fieldName);
		StringBuilder sb=new StringBuilder();
		for(int i = 0; i < fieldName.length;i++){
			String keyName=fieldName[i];
			sb.append(keyMap.get(keyName));
			if(i == fieldName.length - 1){
				break;
			}
			sb.append("#");
		}
		return sb.toString();
	}
	

	private static Field getDeclaredField(Object object, String fieldName){
		Class<?> clazz = object.getClass();
		for(; clazz != Object.class ; clazz = clazz.getSuperclass()) {
			try {
				return clazz.getDeclaredField(fieldName);
			} catch (Exception e) {
			}
		}
		return null;
    }
	private static Method getSetMethod(Object object,String method,Class<?> fieldType){
		Class<?> clazz=object.getClass();
		for(;clazz!=Object.class;clazz=clazz.getSuperclass()){
			try {
				return clazz.getDeclaredMethod(method, fieldType);
			} catch (Exception e) {
			}
		}
		return null;
	}
	@SuppressWarnings("unchecked")
	public static <T> T getObjFromMap(Map<String,String> map, Object obj){
		try {
			for(String key:map.keySet()){
				Field field=getDeclaredField(obj, key);
				Method method=getSetMethod(obj, buildSetMethod(key), field.getType());
				if(field.getType()==Integer.class||field.getType()==int.class){
					method.invoke(obj, Integer.parseInt(map.get(key)));
				}else if(field.getType()==Boolean.class||field.getType()==boolean.class){
					method.invoke(obj, Boolean.parseBoolean(map.get(key)));
				}else if(field.getType()==Long.class||field.getType()==long.class){
					method.invoke(obj, Long.parseLong(map.get(key)));
				}else if(field.getType()==Float.class||field.getType()==float.class){
					method.invoke(obj, Float.parseFloat(map.get(key)));
				}else if(field.getType()==Double.class||field.getType()==double.class){
					method.invoke(obj, Double.parseDouble(map.get(key)));
				}else if(field.getType()==Byte.class||field.getType()==byte.class){
					method.invoke(obj, Byte.parseByte(map.get(key)));
				}else if(field.getType()==Short.class||field.getType()==short.class){
					method.invoke(obj, Short.parseShort(map.get(key)));
				}else if(field.getType()==String.class){
					method.invoke(obj, map.get(key));
				}else if(field.getType()==Date.class){
					method.invoke(obj, TimeUtils.stringToDate(map.get(key)));
				}else if(field.getType()==Timestamp.class){
					method.invoke(obj, TimeUtils.stringtoTimestamp(map.get(key)));
				}
			}
			return (T)obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 从map里构造出一个实例对象
	 * @param map
	 * @param clazz
	 * @return
	 */
	public static <T> T getObjFromMap(Map<String,String> map,Class<?> clazz){
		try {
			Object obj=clazz.newInstance();
			return getObjFromMap(map, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	private static String buildSetMethod(String fieldName){
		StringBuffer sb=new StringBuffer("set");
		if(fieldName.length()>1){
			String first=fieldName.substring(0, 1);
			String next=fieldName.substring(1);
			sb.append(first.toUpperCase()).append(next);
		}else{
			sb.append(fieldName.toUpperCase());
		}
		return sb.toString();
	}
	/**
	 * 判断某个list是否没有数据
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> boolean isEmpityList(List<T> list){
		boolean b = false;
		if(list==null||list.isEmpty()){
			b=true;
		}
		return b;
	}
}
