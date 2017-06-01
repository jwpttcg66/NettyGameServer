package com.snowcattle.game.db.util;

import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jwp on 2017/2/28.
 */
public class ObjectUtils {

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

    public static Object getFieldsValueObj(Object obj,String fieldName){
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
        return getObjectString(o);
    }

    public static String getObjectString(Object object){
        //如果为空则输出为""
        if(object == null){
            return "";
        }
        if(object instanceof Date){
            return TimeUtils.dateToString((Date)object);
        }
        return object.toString();
    }

    public static Map<String, String> getTransferMap(Map<String, Object> map){
        Map<String, String> transferMap = new HashMap<>();
        for(Map.Entry<String, Object> entry: map.entrySet()){
            transferMap.put(entry.getKey(), getObjectString(entry.getValue()));
        }
        return transferMap;
    }

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
                String value = map.get(key);
                //如果为空放弃，默认设置为空
                if(StringUtils.isEmpty(value)){
                    continue;
                }
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

//    public Object getReflectObject(String ClassName){
//
//    }
}

