package com.snowcattle.game.db.service.redis;

import java.io.Serializable;

/**
 * @author b053-mac
 *	redis分页数据接口
 *  分页接口 需要额外填写zset 需要提供key,score
 */
public interface RedisPageInterface {
	public RedisKeyEnum getPageRedisKeyEnum();
	public Serializable getScore();
}
