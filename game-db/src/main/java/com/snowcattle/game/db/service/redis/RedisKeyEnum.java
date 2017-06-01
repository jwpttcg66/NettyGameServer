package com.snowcattle.game.db.service.redis;



/**
 * 缓存键值
 */
public enum RedisKeyEnum {
	
	PLAYER("pr#"),
	TOCKEN("tk#"),
	ASYNC_PLAYER("ay_pr"),
	;
	
	private String key;
	
	RedisKeyEnum(String key){
		this.key = key;
	}
	
	public static RedisKeyEnum geRedisKeyEnum(String key){
		RedisKeyEnum result = null;
		for(RedisKeyEnum temp: RedisKeyEnum.values()){
			if(temp.getKey().equals(key)){
				result = temp;
				break;
			}
		}
		return result;
	}
	
	public String getKey(){
		return this.key;
	}
}