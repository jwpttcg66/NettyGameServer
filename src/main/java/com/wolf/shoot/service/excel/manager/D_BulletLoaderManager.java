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
import com.wolf.shoot.service.excel.entity.Emitter;
import com.wolf.shoot.service.excel.entity.EmitterAttr;

public class D_BulletLoaderManager {
	private static final Logger logger = Loggers.utilLogger;
	public static D_BulletLoaderManager bulletLoaderManager = null;
	public static D_BulletLoaderManager getInstance(){
		if(bulletLoaderManager == null){
			bulletLoaderManager = new D_BulletLoaderManager();
		}
		return bulletLoaderManager;
	}
	private Map<Integer, Bullet> bullets = new HashMap<>();
	private Map<Integer, EmitterAttr> emitterAttrs = new HashMap<>();
	private Map<Integer, Emitter> emitters = new HashMap<>();
	public void load(){
		ClassLoader loader = getClass().getClassLoader();
		URL d_bulletResource = loader.getResource("data/bullet/d_bullet.wg");
		URL d_emitter_attrResource = loader.getResource("data/bullet/d_emitter_attr.wg");
		URL d_emitterResource = loader.getResource("data/bullet/d_emitter.wg");
		
		init(d_bulletResource);
		init(d_emitter_attrResource);
		init(d_emitterResource);
	}
	private void init(URL url) {
		BufferedReader d_bulletBr = null;
		try {
			d_bulletBr = new BufferedReader(new FileReader(url.getPath()));
			String s = null;
			StringBuilder sb = new StringBuilder();
			while ((s = d_bulletBr.readLine()) != null){
				sb.append(s);
			}
			JSONObject object = JSONObject.parseObject(sb.toString());
			String tableName = object.getString("class");
			JSONArray body = object.getJSONArray("body");
			if("Bullet".equals(tableName)){
				for (Object o : body) {
					Bullet bullet = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Bullet.class);
					bullets.put(bullet.getID(), bullet);
				}
			}else if("EmitterAttr".equals(tableName)){
				for (Object o : body) {
					EmitterAttr emitterAttr = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), EmitterAttr.class);
					emitterAttrs.put(emitterAttr.getID(), emitterAttr);
				}
			}else if("Emitter".equals(tableName)){
				for (Object o : body) {
					Emitter emitter = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Emitter.class);
					emitters.put(emitter.getID(), emitter);
				}
			}
			
		} catch (IOException e) {
			if(logger.isDebugEnabled()){
				logger.info("解析 "+url.getPath()+" 失败");
			}
		} finally {
			try {
				d_bulletBr.close();
			} catch (IOException e) {
			}
		}
		
	}
	public Map<Integer, Bullet> getBullets(){
		return bullets;
	}
	public Map<Integer, EmitterAttr> getEmitterAttrs(){
		return emitterAttrs;
	}
	public Map<Integer, Emitter> getEmitters(){
		return emitters;
	}
	public Bullet getBullet(int id){
		return bullets.get(id);
	}
}
