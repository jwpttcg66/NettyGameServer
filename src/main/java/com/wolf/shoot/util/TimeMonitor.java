package com.wolf.shoot.util;

/**
 * 时间监测器,用于调试过程
 *
 *
 */
public class TimeMonitor {
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
			System.out.println(p + " time:" + (e - start) + "ms");
		}
	}
}
