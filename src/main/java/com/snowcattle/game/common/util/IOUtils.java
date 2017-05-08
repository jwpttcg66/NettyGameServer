package com.snowcattle.game.common.util;

import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {
	public static void writeLengthString(DataOutput dout, String content,
			String charset) throws IOException {
		if (content == null) {
			content = "";
		}
		byte[] buf = content.getBytes(charset);
		dout.writeShort(buf.length);
		dout.write(buf);
	}

	/**
	 * 关闭输入流，并忽略任何异常
	 * @param in
	 */
	public static void closeInputStream(InputStream in) {
		if (in != null) {
			try {
				in.close();
			} catch (Exception ignore) {
			}
		}
	}

}
