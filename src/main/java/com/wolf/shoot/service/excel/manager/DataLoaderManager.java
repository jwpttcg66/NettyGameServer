package com.wolf.shoot.service.excel.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.snowcattle.game.common.constant.Loggers;
import com.wolf.shoot.service.excel.entity.Item;
import com.wolf.shoot.service.excel.entity.Minimap;
import com.wolf.shoot.service.excel.entity.Model;

public class DataLoaderManager {
	private static final Logger logger = Loggers.utilLogger;
	public static DataLoaderManager bulletLoaderManager = null;
	public static DataLoaderManager getInstance(){
		if(bulletLoaderManager == null){
			bulletLoaderManager = new DataLoaderManager();
		}
		return bulletLoaderManager;
	}
	private Map<Integer, Item> itemss = new HashMap<>();
	private Map<Integer, Minimap> miniMaps = new HashMap<>();
	private Map<Integer, Model> models = new HashMap<>();
	public void load(){
		ClassLoader loader = getClass().getClassLoader();
		URL d_itemResource = loader.getResource("data/data/d_item.wg");
		URL d_minimapResource = loader.getResource("data/data/d_minimap.wg");
		URL d_modelResource = loader.getResource("data/data/d_model.wg");
		BufferedReader d_itemBr = null;
		BufferedReader d_minimapBr = null;
		BufferedReader d_modelBr = null;
		init(d_itemResource);
		init(d_minimapResource);
		init(d_modelResource);
		try {
			d_itemBr = new BufferedReader(new FileReader(d_itemResource.getPath()));
			String s = null;
			StringBuilder sb = new StringBuilder();
			while ((s = d_itemBr.readLine()) != null){
				sb.append(s);
			}
			JSONObject object = JSONObject.parseObject(sb.toString());
			JSONArray body = object.getJSONArray("body");
			for (Object o : body) {
				Item item = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Item.class);
				itemss.put(item.getID(), item);
			}
		} catch (IOException e) {
			if(logger.isDebugEnabled()){
				logger.info("解析 "+d_itemResource.getPath()+" 失败");
			}
		} finally {
			try {
				d_itemBr.close();
			} catch (IOException e) {
			}
		}
		
		
		
		
		try{
			d_minimapBr = new BufferedReader(new FileReader(d_minimapResource.getPath()));
			String d_minimapStr = null;
			StringBuilder d_minimapSB = new StringBuilder();
			while ((d_minimapStr = d_minimapBr.readLine()) != null){
				d_minimapSB.append(d_minimapStr);
			}
			JSONObject d_minimapObject = JSONObject.parseObject(d_minimapSB.toString());
			JSONArray d_minimapbody = d_minimapObject.getJSONArray("body");
			for (Object o : d_minimapbody) {
				Minimap minimap = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Minimap.class);
				miniMaps.put(minimap.getID(), minimap);
			}
		}catch(IOException e){
			if(logger.isDebugEnabled()){
				logger.debug("解析 "+d_minimapResource.getPath()+" 失败");
			}
		}finally{
			try {
				d_minimapBr.close();
			} catch (IOException e) {
			}
		}
		
		try{
			d_modelBr = new BufferedReader(new FileReader(d_modelResource.getPath()));
			String d_modelStr = null;
			StringBuilder d_modelSB = new StringBuilder();
			while ((d_modelStr = d_modelBr.readLine()) != null){
				d_modelSB.append(d_modelStr);
			}
			JSONObject d_modelObject = JSONObject.parseObject(d_modelSB.toString());
			JSONArray d_modelbody = d_modelObject.getJSONArray("body");
			for (Object o : d_modelbody) {
				Model model = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Model.class);
				models.put(model.getID(), model);
			}
		}catch(IOException e){
			if(logger.isDebugEnabled()){
				logger.info("解析 "+d_modelResource.getPath()+" 失败");
			}
		}finally{
			try {
				d_modelBr.close();
			} catch (IOException e) {
			}
		}
	}
	public void init(URL url){
		BufferedReader d_itemBr = null;
		try {
			d_itemBr = new BufferedReader(new FileReader(url.getPath()));
			String s = null;
			StringBuilder sb = new StringBuilder();
			while ((s = d_itemBr.readLine()) != null){
				sb.append(s);
			}
			JSONObject object = JSONObject.parseObject(sb.toString());
			JSONArray body = object.getJSONArray("body");
			String tableName = object.getString("class");
			if("Item".equals(tableName)){
				for (Object o : body) {
					Item item = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Item.class);
					itemss.put(item.getID(), item);
				}
			}else if("Minimap".equals(tableName)){
				for (Object o : body) {
					Minimap minimap = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Minimap.class);
					miniMaps.put(minimap.getID(), minimap);
				}
			}else if("Model".equals(tableName)){
				for (Object o : body) {
					Model model = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Model.class);
					models.put(model.getID(), model);
				}
			}
		} catch (IOException e) {
			if(logger.isDebugEnabled()){
				logger.info("解析 "+url.getPath()+" 失败");
			}
		} finally {
			try {
				d_itemBr.close();
			} catch (IOException e) {
			}
		}
	}
}
