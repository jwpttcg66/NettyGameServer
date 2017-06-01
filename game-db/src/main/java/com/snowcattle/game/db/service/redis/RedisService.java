package com.snowcattle.game.db.service.redis;


import com.snowcattle.game.db.common.GlobalConstants;
import com.snowcattle.game.db.common.Loggers;
import com.snowcattle.game.db.entity.IEntity;
import com.snowcattle.game.db.util.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

import java.util.*;
import java.util.Map.Entry;

/**
 * Created by jiangwenping on 17/3/16.
 * 缓存服务
 */

@Service
public class RedisService{

	protected static Logger logger = Loggers.dbLogger;
	/*
	 * 数据源
	 */
	private JedisPool jedisPool;
	/**
	 * 设置连接池
	 */
	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}
	
	/*
	 * 正常返还链接
	 */
	private void returnResource(Jedis jedis) {
		try {
			jedisPool.returnResource(jedis);
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}
	/*
	 * 释放错误链接
	 */
	private void returnBrokenResource(Jedis jedis,String name,Exception msge){
		logger.error(TimeUtils.dateToString(new Date())+":::::"+name+":::::"+msge.getMessage(), msge);
		if (jedis != null) {
			try {
				jedisPool.returnBrokenResource(jedis);
			} catch (Exception e) {
				logger.error(e.toString(), e);
			}
		}
	}
	
	/**
	 * 设置缓存生命周期
	 * @param key
	 * @param seconds
	 */
	public void expire(String key,int seconds){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis=jedisPool.getResource();
			jedis.expire(key, seconds);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "expire:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
	}
	/**
	 * 将对象保存到hash中,并且设置默认生命周期
	 * @param key
	 * @param entity
	 */
	public void setObjectToHash(String key, IEntity entity){
		setObjectToHash(key, entity, GlobalConstants.RedisKeyConfig.NORMAL_LIFECYCLE);
	}
	/**
	 * 将对象保存到hash中,并且设置生命周期
	 * @param key
	 * @param entity
	 * @param seconds
	 */
	public boolean setObjectToHash(String key,IEntity entity,int seconds){
		Jedis jedis = null;
		boolean sucess = true;
		try{
			jedis=jedisPool.getResource();
			Map<String, String> map = EntityUtils.getCacheValueMap(entity);
			jedis.hmset(key, map);
			if(seconds>=0){
				jedis.expire(key, seconds);
			}
		}catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "setObjectToHash:"+key, e);
		}finally{
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return sucess;
	}
	/*
	 * 更新缓存里的hash值
	 * @param key
	 * @param map
	 * @param unique 此key是由哪个字段拼接而成的
	 */
	
	public void updateObjectHashMap(String key,Map<String,Object> map){
		Jedis jedis = null;
		boolean sucess = true;
		try{
			Map<String,String> mapToUpdate=new HashMap<String, String>();
			for(Entry<String, Object> entry:map.entrySet()){
				String temp=entry.getKey();
				Object obj=entry.getValue();
				if(obj instanceof Date){
					mapToUpdate.put(temp, TimeUtils.dateToString((Date)obj));
				}else{
					mapToUpdate.put(temp, obj.toString());
				}
			}
			jedis=jedisPool.getResource();
			jedis.hmset(key, mapToUpdate);
		}catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "updateHashMap:"+key, e);
		}finally{
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
	}
	/*
	 * 更新缓存里的field字段值
	 * @param key
	 * @param field
	 * @param value 此key是由哪个字段拼接而成的
	 */
	
	public Long hincrBy(String key,String field,int value){
		Jedis jedis = null;
		boolean sucess = true;
		Long result = -1L;
		try{
			jedis=jedisPool.getResource();
			result = jedis.hincrBy(key, field, value);
		}catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "hincrBy:"+key + ":field"+ field, e);
		}finally{
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		
		return result;
	}
	/**
	 * 通过反射从缓存里获取一个对象 缺省默认时间
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public <T> T getObjectFromHash(String key,Class<?> clazz){
		return (T)getObjectFromHash(key, clazz, GlobalConstants.RedisKeyConfig.NORMAL_LIFECYCLE);
	}

	/*
	 * 通过反射从缓存里获取一个对象
	 * @param key
	 * @param clazz
	 * @param uniqueKey 此key由哪个字段拼接而成的
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObjectFromHash(String key,Class<?> clazz, int seconds){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis=jedisPool.getResource();
			Map<String, String> map=jedis.hgetAll(key);
			if(map.size()>0){
				Object obj = clazz.newInstance();
				if(seconds>=0){
					jedis.expire(key, seconds);
				}
				return (T) ObjectUtils.getObjFromMap(map, obj);
			}
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "getObjectFromHash:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return null;
	}
	/**
	 * 将一个列表对象放入缓存
	 * @param key
	 * @param list
	 */
	
	public void setListToHash(String key,List<RedisListInterface> list){
		setListToHash(key, list, GlobalConstants.RedisKeyConfig.NORMAL_LIFECYCLE);
	}
	/*
	 * 将一个列表对象放入缓存，并设置有效期
	 * @param key
	 * @param list
	 * @param seconds
	 */
	public boolean setListToHash(String key,List<RedisListInterface> list,int seconds){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			Map<String,String> map=new HashMap<String, String>();
			Map<String,String> keyMap=null;
			String[] keyNames=null;
			for(RedisListInterface po:list){
				map.put(po.getSubUniqueKey(), JsonUtils.getJsonStr(EntityUtils.getCacheValueMap((IEntity) po)));
			}
			jedis=jedisPool.getResource();
			jedis.hmset(key, map);
			if(seconds >= 0){
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "setListToHash:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return sucess;
	}

	/**
	 * 从缓存里还原一个列表对象
	 * @param key
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")

	public <T> List<T> getListFromHash(String key,Class<?> clazz){
		return getListFromHash(key, clazz, GlobalConstants.RedisKeyConfig.NORMAL_LIFECYCLE);
	}

	/**
	 * 从缓存里还原一个列表对象
	 * @param key
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	
	public <T> List<T> getListFromHash(String key,Class<?> clazz,int seconds){
		Jedis jedis = null;
		boolean sucess = true;
		Map<String,String> map=null;
		try {
			jedis=jedisPool.getResource();
			map=jedis.hgetAll(key);
			if(map != null && map.size()>0){
				List<T> rt=new ArrayList<T>();
				RedisListInterface po=null;
				Map<String,String> mapFields=null;
				String keyNames[]=null;
				for(Entry<String, String> entry:map.entrySet()){
					String fieldKey = entry.getKey();
					mapFields=JsonUtils.getMapFromJson(entry.getValue());
					po = (RedisListInterface) clazz.newInstance();
					ObjectUtils.getObjFromMap(mapFields, po);
					rt.add((T)po);
				}
				if(seconds >= 0){
					jedis.expire(key, seconds);
				}
				return rt;
			}
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "getListFromHash:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return null;
	}
	
//	/**
//	 * 从缓存里还原一个列表对象
//	 * @param key
//	 * @param clazz
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//
//	public <T> T getObjectFromList(String key,String subUnionkey, Class<?> clazz,int seconds){
//		Jedis jedis = null;
//		boolean sucess = true;
//		Map<String,String> map=null;
//		try {
//			jedis=jedisPool.getResource();
//			String value = jedis.hget(key, subUnionkey);
//			if(StringUtils.isEmpty(value)){
//				return null;
//			}
//			RedisListInterface po=null;
//			Map<String,String> mapFields=null;
//			mapFields=JsonUtils.getMapFromJson(value);
//			po = (RedisListInterface) clazz.newInstance();
//			ObjectUtils.getObjFromMap(mapFields, po);
//			if(seconds >= 0){
//				jedis.expire(key, seconds);
//			}
//			return (T) po;
//		} catch (Exception e) {
//			sucess = false;
//			returnBrokenResource(jedis, "getListFromHash:"+key, e);
//		} finally {
//			if (sucess && jedis != null) {
//				returnResource(jedis);
//			}
//		}
//		return null;
//	}
	
	/**
	 * 批量删除对象
	 * @param key
	 * @param list
	 */
	
	public boolean deleteList(String key,List<RedisListInterface> list){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			String keys[]=new String[list.size()];
			String keyNames[]=null;
			Map<String,String> keyMap=null;
			int index=0;
			for(RedisListInterface po:list){
				keys[index++]=ObjectUtils.getFieldsValueStr(po, po.getSubUniqueKey());
			}
			jedis=jedisPool.getResource();
			jedis.hdel(key, keys);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "deleteList:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return sucess;
	}
	
	public void setString(String key,String object){
		setString(key, object, -1);
	}
	
	/**
	 * 设置
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setNxString(String key, String value, int seconds) throws Exception{
		Jedis jedis = null;
		boolean success = true;
		boolean result = false;
		try {
			jedis = jedisPool.getResource();
			result = (jedis.setnx(key, value) != 0);
			if(seconds > -1){
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			success = false;
			releasBrokenReidsSource(jedis, key, "setNxString", e, false);
			throw e;
		} finally {
			releaseReidsSource(success, jedis);
		}
		
		return result;
		
	}
	
	/**
	 * 设置
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setHnxString(String key, String field, String value) throws Exception{
		Jedis jedis = null;
		boolean success = true;
		boolean result = false;
		try {
			jedis = jedisPool.getResource();
			result = (jedis.hsetnx(key, field, value) != 0);
		} catch (Exception e) {
			success = false;
			releasBrokenReidsSource(jedis, key, "setHnxString", e, false);
			throw e;
		} finally {
			releaseReidsSource(success, jedis);
		}
		
		return result;
		
	}
	
	/**
	 * 必须强制获取成功状态
	 * @param key
	 * @param field
	 * @return
	 */
	public String getHgetString(String key, String field) throws Exception{
		Jedis jedis = null;
		boolean success = true;
		String getResult = null;
		try {
			jedis = jedisPool.getResource();
			getResult = jedis.hget(key, field);
		} catch (Exception e) {
			success = false;
			releasBrokenReidsSource(jedis, key, "getHString", e, false);
			throw e;
		} finally {
			releaseReidsSource(success, jedis);
		}
		
		return getResult;
	}
	
	/**
	 * 删除key
	 * @param key
	 */
	public boolean deleteHField(String key, String field){
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = jedisPool.getResource();
			jedis.hdel(key, field);
		} catch (Exception e) {
			success = false;
			releasBrokenReidsSource(jedis, key, "deleteHField", e, false);
		} finally {
			releaseReidsSource(success, jedis);
		}
		return success;
	}
	
	
	/**
	 * 删除key
	 * @param key
	 */
	public boolean deleteKey(String key){
		Jedis jedis = null;
		boolean success = true;
		try {
			jedis = jedisPool.getResource();
			jedis.del(key);
		} catch (Exception e) {
			success = false;
			releasBrokenReidsSource(jedis, key, "deleteKey", e, false);
		} finally {
			releaseReidsSource(success, jedis);
		}
		
		return success;
	}
	

	/**
	 * 获取所有成员及分数
	 * @param key
	 */
	public Set<Tuple> zAllMemberWithScore(String key){
		Jedis jedis = null;
		boolean success = true;
		Set<Tuple> set = null;
		try {
			jedis = jedisPool.getResource();
			set = jedis.zrevrangeWithScores(key, 0, -1);
		} catch (Exception e) {
			success = false;
			releasBrokenReidsSource(jedis, key, "zAllMemberWithScore", e, false);
		} finally {
			releaseReidsSource(success, jedis);
		}
		
		return set;
	}
	
	
	/**
	 * 排序
	 * @param key
	 */
	public Set<String> zRevRange(String key, long start, long end){
		Jedis jedis = null;
		boolean success = true;
		Set<String> set = null;
		try {
			jedis = jedisPool.getResource();
			set = jedis.zrevrange(key, start, end);
		} catch (Exception e) {
			success = false;
			releasBrokenReidsSource(jedis, key, "deleteKey", e, false);
		} finally {
			releaseReidsSource(success, jedis);
		}
		
		return set;
	}
	
	/**
	 * 删除key
	 */
	public void deleteKeys(String... keys){
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			jedis.del(keys);
		} catch (Exception e) {
			returnBrokenResource(jedis, "deleteKey" + keys, e);
		} finally {
			releaseReidsSource(true, jedis);
		}
		
	}
	/**
	 * 释放非正常链接
	 * @param jedis
	 * @param key
	 * @param string
	 * @param e
	 */
	private void releasBrokenReidsSource(Jedis jedis, String key, String string, Exception e, boolean deleteKeyFlag){
		returnBrokenResource(jedis, string, e);
		if(deleteKeyFlag){
			expire(key, 0);
		}
	}
	
	/**
	 * 释放成功链接
	 * @param success
	 * @param jedis
	 */
	private void releaseReidsSource(boolean success, Jedis jedis){
		if (success && jedis != null) {
			returnResource(jedis);
		}
	}
	
	public void setString(String key, String value, int seconds) {
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			jedis.set(key, value);
			if(seconds>-1){
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "setString", e);
			expire(key, 0);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
	}
	
	public String getString(String key){
		return getString(key, -1);
	}
	
	public String getString(String key, int seconds){
		Jedis jedis = null;
		boolean sucess = true;
		String rt = null;
		try {
			jedis = jedisPool.getResource();
			rt = jedis.get(key);
			if(seconds > -1){
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "getString", e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return rt;
	}
	
	/**
	 * 需要知道是否是成功获取的
	 * @param key
	 * @return
	 */
	public Object[] getStringAndSuccess(String key){
		Jedis jedis = null;
		boolean sucess = true;
		String rt = "";
		try {
			jedis = jedisPool.getResource();
			rt = jedis.get(key);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "getString", e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		Object[] object = new Object[2];
		object[0] = sucess;
		object[1] = rt;
		return object;
	}
	public List<String> hvals(String key){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			return jedis.hvals(key);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "hvals", e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return null;
	}
	public boolean hexists(String key , String field){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			return jedis.hexists(key, field);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "hexists", e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return false;
	}
	
	/**
	 * 检测是否是成员
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean sexists(String key , String member){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			return jedis.sismember(key, member);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "hexists", e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return false;
	}
	
	public Long hdel(String key , String ...fields){
		Jedis jedis = null;
		boolean sucess = true;
		Long rt = -1L;
		try {
			jedis = jedisPool.getResource();
			rt = jedis.hdel(key, fields);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "hdel", e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return rt;
	}

	public List<String> mgetString(List<String> keys){
		Jedis jedis = null;
		boolean sucess = true;
		List<String> rt=new ArrayList<String>();
		if(ObjectUtils.isEmpityList(keys)){
			return rt;
		}
		try {
			jedis = jedisPool.getResource();
			if(keys.size()> GlobalConstants.RedisKeyConfig.MGET_MAX_KEY){
				List<String> tmp=new ArrayList<String>();
				int size = keys.size();
				int page = size / GlobalConstants.RedisKeyConfig.MGET_MAX_KEY + ((size % GlobalConstants.RedisKeyConfig.MGET_MAX_KEY)>0?1:0);
				for(int i=0;i<page;i++){
					tmp.addAll(PageUtils.getSubListPage(keys, i * GlobalConstants.RedisKeyConfig.MGET_MAX_KEY, GlobalConstants.RedisKeyConfig.MGET_MAX_KEY));
					rt.addAll(jedis.mget(tmp.toArray(new String[0])));
					tmp.clear();
				}
			}else{
				String[] keys2 = keys.toArray(new String[0]);
				rt = jedis.mget(keys2);
			}
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "mgetString"+keys, e);
		}finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return rt;
	}
	
	public void hsetString(String key,String field,String value){
		hsetString(key, field, value, GlobalConstants.RedisKeyConfig.NORMAL_LIFECYCLE);
	}
	
	public void hsetString(String key, String field, String value, int seconds){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			jedis.hset(key,field,value);
			if(seconds != -1){
				jedis.expire(key, seconds);
			}
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "hsetString"+key, e);
			expire(key, 0);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
	}
	
	public Map<String,String> hmgetAllString(String key){
		Jedis jedis = null;
		boolean sucess = true;
		Map<String,String> rt = null;
		try {
			jedis = jedisPool.getResource();
			rt = jedis.hgetAll(key);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "hmgetAllString" + key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return rt;
	}
	
	public String hget(String key,String field){
		Jedis jedis = null;
		boolean sucess = true;
		String rt = null;
		try {
			jedis = jedisPool.getResource();
			rt = jedis.hget(key, field);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "hmgetString"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return rt;
	}
	
	public long hLen(String key){
		Jedis jedis = null;
		boolean sucess = true;
		long rt = -1;
		try {
			jedis = jedisPool.getResource();
			rt = jedis.hlen(key);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "hLen"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return rt;
	}
	/**返回的是列表的剩余个数*/
	public long lpushString(String key,String value){
		Jedis jedis = null;
		boolean sucess = true;
		long result = 0;
		try {
			jedis = jedisPool.getResource();
			result = jedis.lpush(key, value);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "lpushString"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return result;
	}

	/**返回的是列表的剩余个数*/
	public long rPushString(String key,String value){
		Jedis jedis = null;
		boolean sucess = true;
		long result = 0;
		try {
			jedis = jedisPool.getResource();
			result = jedis.rpush(key, value);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "rpushString"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return result;
	}


	/**
	 * 返回集合key的基数(集合中元素的数量)。
	 * @param key
	 * @return
	 */
	public long scardString(String key){
		Jedis jedis = null;
		boolean sucess = true;
		long rt = 0L;
		try {
			jedis = jedisPool.getResource();
			rt = jedis.scard(key);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "scardString"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return rt;
	}
	
	public long saddString(String key,String value){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			Long ret = jedis.sadd(key,value);
			return ret;
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "saddString"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return 0;
	}

	public void saddStrings(String key,String... values){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			jedis.sadd(key, values);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "saddStrings"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
	}
	
	public void sremString(String key,String value){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			jedis.srem(key, value);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "sremString"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
	}

	public void sremStrings(String key,String... values){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			jedis.srem(key,values);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "sremStrings"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
	}
	
	public String spopString(String key){
		Jedis jedis = null;
		boolean sucess = true;
		String rt = null;
		try {
			jedis = jedisPool.getResource();
			rt = jedis.spop(key);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "spopString"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return rt;
	}
	
	public Set<String> smembersString(String key){
		Jedis jedis = null;
		boolean sucess = true;
		Set<String> rt = null;
		try {
			jedis = jedisPool.getResource();
			rt = jedis.smembers(key);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "smembersString"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return rt;
	}
	/**
	 * 删除zset 的成员
	 * @param key
	 * @param member
	 * @return
	 */
	public long zRemByMember (String key ,String member){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			return jedis.zrem(key, member);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "zrangeByScoreWithScores", e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return 0;
	}

	/**
	 * 删除zset 的成员
	 * @param key
	 * @param member
	 * @return
	 */
	public boolean zRemByMemberReturnBoolean (String key ,String member){
		Jedis jedis = null;
		boolean sucess = true;
		boolean result = false;
		try {
			jedis = jedisPool.getResource();
			result = (jedis.zrem(key, member) !=0);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "zrangeByScoreWithScores", e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return result;
	}

	public Set<String> zrangeByScore(String key, long min, long max, int limit){
		Jedis jedis = null;
		boolean sucess = true;
		Set<String> ret = null;
		try {
			jedis = jedisPool.getResource();
			ret = jedis.zrangeByScore(key, min, max, 0, limit);
			return ret;
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "zrangeByScore", e);
			return null;
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
	}
	
	/**
	 * 增加成员
	 * @param key
	 * @return
	 */
	public boolean zAdd(String key, String member, long value){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			jedis.zadd(key, value, member);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "zAdd key:"+key + "member:" + member + "value:"+ value, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return sucess;
	}
	
	/**
	 * 增加值
	 * @param key
	 * @param member
	 * @param value
	 * @return
	 */
	public boolean zIncrBy(String key, String member, long value){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			//记录最后一个心跳时间
			jedis.zincrby(key, value, member);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "zIncrBy key:"+key + "member:" + member + "value:"+ value, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return sucess;
	}
	
	/**
	 * 增加成员
	 * @return
	 */
	public boolean zAddMap(String key, Map<String, Double> scoreMembers){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			//记录最后一个心跳时间
			jedis.zadd(key, scoreMembers);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "zAddMap key:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return sucess;
	}

	public Long incr(String key){
		Jedis jedis = null;
		boolean sucess = true;
		long result = -1;
		try {
			jedis = jedisPool.getResource();
			result = jedis.incr(key);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "incr:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return result;
	}
	
	public Long incrBy(String key, long value){
		Jedis jedis = null;
		boolean sucess = true;
		long result = -1;
		try {
			jedis = jedisPool.getResource();
			result = jedis.incrBy(key, value);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "incrBy:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return result;
	} 
	
	/**
	 * 返回是否成功
	 * @param key
	 * @return
	 */
	public boolean decr(String key){
		Jedis jedis = null;
		boolean sucess = true;
		long result = -1;
		try {
			jedis = jedisPool.getResource();
			jedis.decr(key);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "decr:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return sucess;
	} 
	
	/**
	 * 返回是否成功
	 * @param key
	 * @return
	 */
	public boolean decrBy(String key, int size){
		Jedis jedis = null;
		boolean sucess = true;
		long result = -1;
		try {
			jedis = jedisPool.getResource();
			jedis.decrBy(key, size);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "decrBy:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return sucess;
	} 
	public boolean exists(String key){
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			return jedis.exists(key);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "exists:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return false;
	}
	
	/**
	 * 返回在分数之类的所有的成员以及分数.
	 */
	public Set<Tuple> zrangeByScoreWithScores(String key, long min, long max, int offset, int limit){
		Jedis jedis = null;
		boolean sucess = true;
		Set<Tuple> ret = null;
		try {
			jedis = jedisPool.getResource();
			ret = jedis.zrangeByScoreWithScores(key, min, max, offset, limit);
			return ret;
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "zrangeByScoreWithScores limit", e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return ret;
	}
	
	/**
	 * 获取包含这个key的所有redis key
	 * @param key
	 * @return
	 */
	public Set<String> keys(String key){
		Jedis jedis = null;
		boolean sucess = true;
		Set<String> keys = null;
		try {
			jedis = jedisPool.getResource();
			keys = jedis.keys("*" + key + "*");
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "keys", e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return keys;
	}
	
	/**
	 * 获取key的剩余时间
	 * @param key
	 * @return
	 */
	public long getKeyTTL(String key){
		Jedis jedis = null;
		long result = 0;
		Set<String> keys = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			result = jedis.ttl(key);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "key ttl", e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return result;
	}
	
	
	/**
	 * 设置
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean setNxStringByMillisec(String key, String value, int millisec){
		Jedis jedis = null;
		boolean success = true;
		boolean result = false;
		try {
			jedis = jedisPool.getResource();
			result = (jedis.setnx(key, value) != 0);
			if(millisec > -1){
				jedis.pexpire(key, millisec);
			}
		} catch (Exception e) {
			success = false;
			releasBrokenReidsSource(jedis, key, "setNxStringByMillisec", e, false);
		} finally {
			releaseReidsSource(success, jedis);
		}
		
		return result;
		
	}
	
	public List<String> lrange(String key, int start, int stop) {
		Jedis jedis = null;
		boolean sucess = true;
		List<String> ret = null;
		try {
			jedis = jedisPool.getResource();
			ret = jedis.lrange(key, start, stop);
			return ret;
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "lrange", e);
			return null;
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
	}
	
	public long rpush(String key, String value) {
		Jedis jedis = null;
		boolean sucess = true;
		long ret = -1;
		try {
			jedis = jedisPool.getResource();
			ret = jedis.rpush(key, value);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "rpush key:"+key + "value:"+ value, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return ret;
	}
	
	public String lpop(String key) {
		Jedis jedis = null;
		boolean sucess = true;
		String ret = null;
		try {
			jedis = jedisPool.getResource();
			ret = jedis.lpop(key);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "lpop key:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return ret;
	}

	public String lindex(String key) {
		Jedis jedis = null;
		boolean sucess = true;
		String ret = null;
		try {
			jedis = jedisPool.getResource();
			ret = jedis.lindex(key, 0);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "lpop key:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return ret;
	}
	
	public long zcount(String key){
		long size = 0;
		Jedis jedis = null;
		boolean sucess = true;
		try {
			jedis = jedisPool.getResource();
			size = jedis.zcount(key, 0, Long.MAX_VALUE);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "zcount key:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return size;
	}

	public String sRandMember(String key){
		Jedis jedis = null;
		boolean sucess = true;
		String ret = null;
		try {
			jedis = jedisPool.getResource();
			ret = jedis.srandmember(key);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "lpop key:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return ret;
	}

	public List<String> sRandMember(String key, int count){
		Jedis jedis = null;
		boolean sucess = true;
		List<String> ret = null;
		try {
			jedis = jedisPool.getResource();
			ret = jedis.srandmember(key, count);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "sRandMember key:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return ret;
	}

	//删除集合里的元素
	public Long lRem(String key, int count, String value){
		Jedis jedis = null;
		boolean sucess = true;
		long ret = 0;
		try {
			jedis = jedisPool.getResource();
			ret = jedis.lrem(key, count, value);
		} catch (Exception e) {
			sucess = false;
			returnBrokenResource(jedis, "lRem key:"+key, e);
		} finally {
			if (sucess && jedis != null) {
				returnResource(jedis);
			}
		}
		return ret;
	}
}
