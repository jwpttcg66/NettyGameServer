package com.snowcattle.game.db.service.redis;

/**
 * Created by jiangwenping on 17/3/16.
 * 默认保存为map, 保证更新不会被覆盖
 */
public interface RedisInterface {
	public String getUnionKey();
	public String getRedisKeyEnumString();
}

