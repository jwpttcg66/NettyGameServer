package com.snowcattle.game.service.time;

/**
 * 提供时间相关的服务
 *
 *
 */
public interface TimeService {
	/**
	 * 当前时间
	 *
	 * @return
	 */
	public long now();

	/**
	 * 更新当前时间，只有在当前时间服务使用了缓存策略的情况下才需要调用此方法
	 */
	public void update();

	/**
	 * 是否到达某个时间
	 *
	 * @param sometime
	 * @return
	 */
	public boolean timeUp(long sometime);

	/**
	 * 获取指定时间与当前时间的时间差
	 *
	 * @param sometime
	 * @return
	 */
	public long getInterval(long sometime);

	/**
	 * 获取时间偏移量
	 *
	 * @return
	 *
	 */
	public long getOffset();

	/**
	 * 设置时间偏移量
	 *
	 * @param value 单位: 毫秒
	 */
	public void setOffset(long value);
}
