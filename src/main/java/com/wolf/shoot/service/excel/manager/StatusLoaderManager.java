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
import com.wolf.shoot.service.excel.entity.DropExp;
import com.wolf.shoot.service.excel.entity.RoleAttr;
import com.wolf.shoot.service.excel.entity.RoleSkill;
import com.wolf.shoot.service.excel.entity.Upgrade;

public class StatusLoaderManager {
	private static final Logger logger = Loggers.utilLogger;
	public static StatusLoaderManager bulletLoaderManager = null;
	public static StatusLoaderManager getInstance(){
		if(bulletLoaderManager == null){
			bulletLoaderManager = new StatusLoaderManager();
		}
		return bulletLoaderManager;
	}
	private Map<Integer, DropExp> dropExps = new HashMap<>();
	private Map<Integer, RoleAttr> attrs = new HashMap<>();
	private Map<Integer, RoleAttr> attrMps = new HashMap<>();
	private Map<Integer, RoleSkill> skills = new HashMap<>();
	private Map<Integer, RoleSkill> skillMps = new HashMap<>();
	private Map<Integer, Upgrade> upgrades = new HashMap<>();
	
	
	public void load(){
		ClassLoader loader = getClass().getClassLoader();
		URL d_drop_expResource = loader.getResource("data/status/d_drop_exp.wg");
		URL d_role_attr_mpResource = loader.getResource("data/status/d_role_attr_mp.wg");
		URL d_role_attrResource = loader.getResource("data/status/d_role_attr.wg");
		URL d_role_skill_mpResource = loader.getResource("data/status/d_role_skill_mp.wg");
		URL d_role_skillResource = loader.getResource("data/status/d_role_skill.wg");
		URL d_upgradeResource = loader.getResource("data/status/d_upgrade.wg");
		
		init(d_drop_expResource);
		init(d_role_attrResource);
		init(d_role_skillResource);
		init(d_upgradeResource);
		
		URL temp = null;
		BufferedReader d_role_attr_mpBr = null;
		BufferedReader d_role_skill_mpBr = null;
		try {
			d_role_attr_mpBr = new BufferedReader(new FileReader(d_role_attr_mpResource.getPath()));
			temp = d_role_attr_mpResource;
			String d_role_attr_mpStr = null;
			StringBuilder d_role_attr_mpSB = new StringBuilder();
			while ((d_role_attr_mpStr = d_role_attr_mpBr.readLine()) != null){
				d_role_attr_mpSB.append(d_role_attr_mpStr);
			}
			JSONObject d_role_attr_mpObject = JSONObject.parseObject(d_role_attr_mpSB.toString());
			JSONArray d_role_attr_mpBody = d_role_attr_mpObject.getJSONArray("body");
			for (Object o : d_role_attr_mpBody) {
				RoleAttr roleAttr = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), RoleAttr.class);
				attrMps.put(roleAttr.getID(), roleAttr);
			}
			
			d_role_skill_mpBr = new BufferedReader(new FileReader(d_role_skill_mpResource.getPath()));
			temp = d_role_skill_mpResource;
			String d_role_skill_mpStr = null;
			StringBuilder d_role_skill_mpSB = new StringBuilder();
			while ((d_role_skill_mpStr = d_role_skill_mpBr.readLine()) != null){
				d_role_skill_mpSB.append(d_role_skill_mpStr);
			}
			JSONObject d_role_skill_mpObject = JSONObject.parseObject(d_role_skill_mpSB.toString());
			JSONArray d_role_skill_mpBody = d_role_skill_mpObject.getJSONArray("body");
			for (Object o : d_role_skill_mpBody) {
				RoleSkill skill = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), RoleSkill.class);
				skillMps.put(skill.getID(), skill);
			}
			
			
		} catch (IOException e) {
			if(logger.isDebugEnabled()){
				logger.debug("解析 "+temp.getPath()+" 失败");
			}
		} finally {
			try {
				d_role_attr_mpBr.close();
				d_role_skill_mpBr.close();
			} catch (IOException e) {
			}
		}
	}


	private void init(URL url) {
		BufferedReader br = null; 
		try{
			br = new BufferedReader(new FileReader(url.getPath()));
			String s = null;
			StringBuilder sb = new StringBuilder();
			while ((s = br.readLine()) != null){
				sb.append(s);
			}
			JSONObject object = JSONObject.parseObject(sb.toString());
			String tableName = object.getString("class");
			JSONArray body = object.getJSONArray("body");
			if("DropExp".equals(tableName)){
				for (Object o : body) {
					DropExp dropExp = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), DropExp.class);
					dropExps.put(dropExp.getID(), dropExp);
				}
			}else if("RoleAttr".equals(tableName)){
				for (Object o : body) {
					RoleAttr roleAttr = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), RoleAttr.class);
					attrs.put(roleAttr.getID(), roleAttr);
				}
			}else if("RoleSkill".equals(tableName)){
				for (Object o : body) {
					RoleSkill target = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), RoleSkill.class);
					skills.put(target.getID(), target);
				}
			}else if("Upgrade".equals(tableName)){
				for (Object o : body) {
					Upgrade upgrade = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Upgrade.class);
					upgrades.put(upgrade.getID(), upgrade);
				}
			}
			for (Object o : body) {
				DropExp dropExp = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), DropExp.class);
				dropExps.put(dropExp.getID(), dropExp);
			}
		}catch(IOException e){
			if(logger.isDebugEnabled()){
				logger.debug("解析 "+url.getPath()+" 失败");
			}
		}finally{
			try {
				br.close();
			} catch (IOException e) {
			}
		}
	}
}
