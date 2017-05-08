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
import com.wolf.shoot.common.constant.Loggers;
import com.wolf.shoot.service.excel.entity.Bullet;
import com.wolf.shoot.service.excel.entity.Demiurge;
import com.wolf.shoot.service.excel.entity.Emitter;
import com.wolf.shoot.service.excel.entity.EmitterAttr;
import com.wolf.shoot.service.excel.entity.Entity;
import com.wolf.shoot.service.excel.entity.EntityAi;
import com.wolf.shoot.service.excel.entity.EntityTarget;
import com.wolf.shoot.service.excel.entity.EntityTrigger;
import com.wolf.shoot.service.excel.entity.Item;
import com.wolf.shoot.service.excel.entity.Minimap;
import com.wolf.shoot.service.excel.entity.Model;

public class EntityLoaderManager {
	private static final Logger logger = Loggers.utilLogger;
	public static EntityLoaderManager bulletLoaderManager = null;
	public static EntityLoaderManager getInstance(){
		if(bulletLoaderManager == null){
			bulletLoaderManager = new EntityLoaderManager();
		}
		return bulletLoaderManager;
	}
	private Map<Integer, Demiurge> demiurges = new HashMap<>();
	private Map<Integer, EntityAi> entityAis = new HashMap<>();
	private Map<Integer, EntityTrigger> triggers = new HashMap<>();
	private Map<Integer, Entity> entitys = new HashMap<>();
	private Map<Integer, EntityTarget> targets = new HashMap<>();
	
	
	public void load(){
		ClassLoader loader = getClass().getClassLoader();
		URL d_demiurgeResource = loader.getResource("data/entity/d_demiurge.wg");
		URL d_entity_aiResource = loader.getResource("data/entity/d_entity_ai.wg");
		URL d_entity_triggerResource = loader.getResource("data/entity/d_entity_trigger.wg");
		URL d_entityResource = loader.getResource("data/entity/d_entity.wg");
		URL d_targetResource = loader.getResource("data/entity/d_target.wg");
		
		init(d_demiurgeResource);
		init(d_entity_aiResource);
		init(d_entity_aiResource);
		init(d_entity_triggerResource);
		init(d_entityResource);
		init(d_targetResource);
	}


	private void init(URL url) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(url.getPath()));
			String s = null;
			StringBuilder sb = new StringBuilder();
			while ((s = br.readLine()) != null){
				sb.append(s);
			}
			JSONObject object = JSONObject.parseObject(sb.toString());
			String tableName = object.getString("class");
			JSONArray body = object.getJSONArray("body");
			if("Demiurge".equals(tableName)){
				for (Object o : body) {
					Demiurge demiurge = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Demiurge.class);
					demiurges.put(demiurge.getID(), demiurge);
				}
			}else if("EntityAi".equals(tableName)){
				for (Object o : body) {
					EntityAi entityAi = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), EntityAi.class);
					entityAis.put(entityAi.getID(), entityAi);
				}
			}else if("EntityTrigger".equals(tableName)){
				for (Object o : body) {
					EntityTrigger trigger = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), EntityTrigger.class);
					triggers.put(trigger.getID(), trigger);
				}
			}else if("Entity".equals(tableName)){
				for (Object o : body) {
					Entity entity = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Entity.class);
					entitys.put(entity.getID(), entity);
				}
			}else if("EntityTarget".equals(tableName)){
				for (Object o : body) {
					EntityTarget target = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), EntityTarget.class);
					targets.put(target.getID(), target);
				}
			}
			
		} catch (IOException e) {
			if(logger.isDebugEnabled()){
				logger.info("解析 "+url.getPath()+" 失败");
			}
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}
		}
		
		
		
		
	}
}
