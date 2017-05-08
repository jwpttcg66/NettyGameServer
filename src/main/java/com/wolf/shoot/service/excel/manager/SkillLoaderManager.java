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
import com.wolf.shoot.service.excel.entity.Buff;
import com.wolf.shoot.service.excel.entity.Bullet;
import com.wolf.shoot.service.excel.entity.Control;
import com.wolf.shoot.service.excel.entity.Demiurge;
import com.wolf.shoot.service.excel.entity.Emitter;
import com.wolf.shoot.service.excel.entity.EmitterAttr;
import com.wolf.shoot.service.excel.entity.Entity;
import com.wolf.shoot.service.excel.entity.EntityAi;
import com.wolf.shoot.service.excel.entity.EntityTarget;
import com.wolf.shoot.service.excel.entity.EntityTrigger;
import com.wolf.shoot.service.excel.entity.Impact;
import com.wolf.shoot.service.excel.entity.Item;
import com.wolf.shoot.service.excel.entity.Knack;
import com.wolf.shoot.service.excel.entity.Minimap;
import com.wolf.shoot.service.excel.entity.Model;
import com.wolf.shoot.service.excel.entity.Skill;
import com.wolf.shoot.service.excel.entity.SkillUpgrade;

public class SkillLoaderManager {
	private static final Logger logger = Loggers.utilLogger;
	public static SkillLoaderManager bulletLoaderManager = null;
	public static SkillLoaderManager getInstance(){
		if(bulletLoaderManager == null){
			bulletLoaderManager = new SkillLoaderManager();
		}
		return bulletLoaderManager;
	}
	private Map<Integer, Buff> buffs = new HashMap<>();
	private Map<Integer, Control> controls = new HashMap<>();
	private Map<Integer, Impact> impacts = new HashMap<>();
	private Map<Integer, Knack> knacks = new HashMap<>();
	private Map<Integer, Skill> skills = new HashMap<>();
	private Map<Integer, SkillUpgrade> skillUpgrades = new HashMap<>();
	
	
	public void load(){
		ClassLoader loader = getClass().getClassLoader();
		URL d_buffResource = loader.getResource("data/skill/d_buff.wg");
		URL d_controlResource = loader.getResource("data/skill/d_control.wg");
		URL d_impactResource = loader.getResource("data/skill/d_impact.wg");
		URL d_knackResource = loader.getResource("data/skill/d_knack.wg");
		URL d_skill_upgradeResource = loader.getResource("data/skill/d_skill_upgrade.wg");
		URL d_skillResource = loader.getResource("data/skill/d_skill.wg");
		
		init(d_buffResource);
		init(d_controlResource);
		init(d_impactResource);
		init(d_knackResource);
		init(d_skill_upgradeResource);
		init(d_skillResource);
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
			if("Buff".equals(tableName)){
				for (Object o : body) {
					Buff buff = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Buff.class);
					buffs.put(buff.getNo(), buff);
				}
			}else if("Control".equals(tableName)){
				for (Object o : body) {
					Control control = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Control.class);
					controls.put(control.getNo(), control);
				}
			}else if("Impact".equals(tableName)){
				for (Object o : body) {
					Impact impact = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Impact.class);
					impacts.put(impact.getNo(), impact);
				}
			}else if("Knack".equals(tableName)){
				for (Object o : body) {
					Knack knack = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Knack.class);
					knacks.put(knack.getID(), knack);
				}
			}else if("SkillUpgrade".equals(tableName)){
				for (Object o : body) {
					SkillUpgrade target = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), SkillUpgrade.class);
					skillUpgrades.put(target.getID(), target);
				}
			}else if("Skill".equals(tableName)){
				for (Object o : body) {
					Skill skill = JSONObject.toJavaObject((JSON)JSONObject.toJSON(o), Skill.class);
					skills.put(skill.getID(), skill);
				}
			}
			
		} catch (Exception e) {
			if(logger.isDebugEnabled()){
				logger.debug("解析 "+url.getPath()+" 失败");
			}
		} finally {
			try {
				br.close();
			} catch (IOException e) {
			}
		}
		
	}
}
