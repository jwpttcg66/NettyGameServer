package com.snowcattle.game.db.service.redis;



/**
 * 列表类型的缓存对象
 * 
 */
public interface RedisListInterface{
	public String getShardingKey();
	public String getRedisKeyEnumString();

	/**
	 * 列表对象的子唯一主键属性
	 */
	public String getSubUniqueKey();
}
