package com.snowcattle.game.common.util;

public class Bytes {
	public static String substring (String src, int start_idx, int end_idx) {
		byte[] b = src.getBytes();
		String tgr = "";
		for(int i = start_idx; i <= end_idx; i++) {
			tgr += (char)b[i];
		}
		return tgr;
	}
}
