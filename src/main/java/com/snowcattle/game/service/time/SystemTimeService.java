package com.snowcattle.game.service.time;

import org.springframework.stereotype.Service;

/**
 *
 * 一般来说,{@link System#currentTimeMillis()} 的调用代价较高,而多数情况下在某一时刻的缓冲时间是可以满足要求的.
 * 在管理器采用由{@link #isCacheTime()}控制的策略,即如果为true就缓冲时间,此时调用{@link #now}取得由
 * {@link #update()}更新的时间;否则取得系统的实时时间
 *
 *
 */
@Service
public class SystemTimeService implements TimeService {
	/** 偏移量 */
	private long offset = 0;

	private Time time;

	/**
	 * 构建一个由cacheTime指定的时间管理里
	 *
	 * @param cacheTime
	 */
	public SystemTimeService() {
		time = new Time();
		time.init();
	}

	/**
	 * 更新时间,如果当前的TimeManqager是启用了缓冲策略,则需要在较短的间隔内定调用该方法更新被缓冲的时间
	 */
	public void update() {
		time.update();
	}

	/**
	 * 获取当前的更新时间,如果{@link #isCacheTime()}为true,即使用缓冲的时间,}
	 *
	 * @return
	 */
	@Override
	public long now() {
		return time.startTime + time.currTime;
	}

	@Override
	public boolean timeUp(long sometime) {
		if (this.now() > sometime) {
			return true;
		}
		return false;
	}

	@Override
	public long getInterval(long sometime) {
		return sometime - this.now();
	}

	/**
	 * 获取时间偏移量, 单位: 毫秒
	 *
	 * @return
	 */
	public long getOffset() {
		return this.offset;
	}

	/**
	 * 设置时间偏移量, <font color='#990000'>为GM命令使用! 用以修改游戏服务器时间</font>
	 *
	 * @param value 时间偏移量, 单位: 毫秒
	 */
	public void setOffset(long value) {
		this.offset = value;
	}
}
