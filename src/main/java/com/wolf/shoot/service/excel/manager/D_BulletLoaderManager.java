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
import com.wolf.shoot.service.excel.Bullet;

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
	public void load(){
		URL resource = getClass().getClassLoader().getResource("data/d_bullet.json");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(resource.getPath()));
			String s = null;
			StringBuilder sb = new StringBuilder();
			while ((s = br.readLine()) != null){
				sb.append(s);
			}
			JSONObject object = JSONObject.parseObject(sb.toString());
			JSONArray body = object.getJSONArray("body");
			for (Object o : body) {
				Bullet bullet = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Bullet.class);
				bullets.put(bullet.getID(), bullet);
			}
		} catch (IOException e) {
			if(logger.isDebugEnabled()){
				logger.info("解析 "+resource.getPath()+" 失败");
			}
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}
		}

	}
	public Map<Integer, Bullet> getBullets(){
		return bullets;
	}
	public Bullet getBullet(int id){
		return bullets.get(id);
	}
}
