package com.snowcattle.game.common.util;

public class MemUtils {

	public static long getUsedMemoryMB() {
		return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
	}

	public static long getFreeMemoryMB() {
		return (Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory() + Runtime.getRuntime().freeMemory()) / 1048576;
	}

	public static long getTotalMemoryMB() {
		return Runtime.getRuntime().maxMemory() / 1048576;
	}

	public static String memoryInfo() {
		long freeMem = MemUtils.getFreeMemoryMB();
		long totalMem = MemUtils.getTotalMemoryMB();
		return "Free memory " + freeMem + " Mb of " + totalMem + " Mb";
	}
}
