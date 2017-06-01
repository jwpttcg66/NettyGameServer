package com.snowcattle.game.common.util;

import com.snowcattle.game.common.constant.Loggers;
import org.slf4j.Logger;

/**
 * 时间监测器,用于调试过程
 *
 *
 */
public class TimeMonitor {
	private Logger logger = Loggers.timeMonitorLogger;
	private final boolean isDebug;
	public static TimeMonitor DEBUG = new TimeMonitor(true);

	private long start = 0;

	public TimeMonitor(boolean isDebug) {
		this.isDebug = isDebug;
	}

	public void _s() {
		if (isDebug) {
			start = System.currentTimeMillis();
		}
	}

	public void _e(String p) {
		if (isDebug) {
			long e = System.currentTimeMillis();
			if(logger.isDebugEnabled()) {
				logger.debug(p + " time:" + (e - start) + "ms");
			}
		}
	}
}
