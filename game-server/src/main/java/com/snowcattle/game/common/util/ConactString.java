package com.snowcattle.game.common.util;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 简单的用于连接字符串操作的对象,相对于StringBuilder有以下改进:
 * <ul>
 * <li>
 * append方法,延迟组装结果(在调用{@link #end()}
 * 的时候才进行内存拷贝操作),避免使用StringBuilder时如果字符串较长时造成的较多的重复内存拷贝</li>
 * <li>
 * getBytes方法,直接对value进行编码,避免String.getBytes的内存拷贝操作</li>
 * </ul>
 * 该类适用于将字符串作为网络数据包发送的场合(即只需要得到byte[]数据,而不需要连接完成后的String对象),同时每次append时字符串较长
 *
  *
 *
 */
public class ConactString {
	private List<String> buffers;
	private int length;
	private char[] value;
	private String valueStr;
	private boolean isEnd = false;

	public ConactString() {
		buffers = new ArrayList<String>();
	}

	public ConactString(int capacity) {
		buffers = new ArrayList<String>(capacity);

	}

	public ConactString append(String str) {
		if (str == null) {
			return this;
		}
		this.buffers.add(str);
		length += str.length();
		return this;
	}

	public ByteBuffer getBytes(String charsetName) {
		if (!isEnd) {
			throw new IllegalStateException("The conact has not been end.");
		}
		final CharsetEncoder encoder = Charset.forName(charsetName).newEncoder().onMalformedInput(
				CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
		final CharBuffer cb = CharBuffer.wrap(this.value, 0, this.value.length);
		try {
			encoder.reset();
			final int len = (int) (this.length * (double) encoder.maxBytesPerChar());
			final ByteBuffer bb = ByteBuffer.allocate(len);
			CoderResult cr = encoder.encode(cb, bb, true);
			if (!cr.isUnderflow())
				cr.throwException();
			cr = encoder.flush(bb);
			if (!cr.isUnderflow())
				cr.throwException();
			return bb;
		} catch (Exception x) {
			throw new Error(x);
		}
	}

	public int length() {
		return this.length;
	}

	public void end() {
		value = new char[this.length];
		int _dest = 0;
		final int _size = this.buffers.size();
		for (int i = 0; i < _size; i++) {
			final String _str = this.buffers.get(i);
			final int _len = _str.length();
			_str.getChars(0, _len, value, _dest);
			_dest = _dest + _len;
		}
		isEnd = true;
		this.buffers = null;
	}

	public String toString() {
		if (valueStr == null) {
			valueStr = new String(value);
		}
		return valueStr;
	}
}
