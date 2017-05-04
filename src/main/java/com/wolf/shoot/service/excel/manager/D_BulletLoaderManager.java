package com.wolf.shoot.service.excel.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.wolf.shoot.common.constant.Loggers;

public class D_BulletLoaderManager {
	private static final Logger logger = Loggers.utilLogger;
	public static D_BulletLoaderManager bulletLoaderManager = null;
	public static D_BulletLoaderManager getInstance(){
		if(bulletLoaderManager == null){
			bulletLoaderManager = new D_BulletLoaderManager();
		}
		return bulletLoaderManager;
	}
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
}
