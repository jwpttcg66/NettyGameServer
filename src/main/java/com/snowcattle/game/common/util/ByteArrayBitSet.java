package com.snowcattle.game.common.util;

/**
 * 以字节数组实现的BitSet
 * <p>
 * 该实现是在初始化对象时即固定位长度的，当获取和设置位时使用的索引上越界时， 将不会自动增加位长度。
 *
 *
 */
public class ByteArrayBitSet {
	private final static int ADDRESS_BITS_PER_BYTE = 3;
	private final static int BITS_PER_BYTE = 1 << ADDRESS_BITS_PER_BYTE;
	/** 每字节索引掩码 */
	private final static int MASK_INDEX_PER_BYTE = BITS_PER_BYTE - 1;

	/** 字节数组 */
	private byte[] bytes;

	/**
	 * @param nbits
	 *            位数,大于0
	 * @exception IllegalArgumentException
	 *                指定位数不大于0时抛出
	 */
	public ByteArrayBitSet(int nbits) {
		if (nbits <= 0) {
			throw new IllegalArgumentException("nbits <= 0: " + nbits);
		}
		initByteArray(nbits);
	}

	/**
	 * 以指定字节数组作为初始化数据
	 *
	 * @param byteArray
	 * @exception NullPointerException
	 *                字节数组为空时
	 * @exception IllegalArgumentException
	 *                在字节数组长度为0时抛出
	 */
	public ByteArrayBitSet(byte[] byteArray) {
		if (byteArray.length == 0) {
			throw new IllegalArgumentException(
					"Length of byte array can't not be zero!");
		}
		bytes = byteArray.clone();
	}

	private void initByteArray(int nbits) {
		bytes = new byte[byteIndex(nbits - 1) + 1];
	}

	private int byteIndex(int bitIndex) {
		return bitIndex >> ADDRESS_BITS_PER_BYTE;
	}

	private int indexMarkInByte(int bitIndex) {
		return 1 << (bitIndex & MASK_INDEX_PER_BYTE);
	}

	/**
	 * 获得指定索引上的位值
	 *
	 * @param bitIndex
	 * @return 返回指定索引上的值，上越界时该值始终返回false
	 * @exception IndexOutOfBoundsException
	 *                在索引小于0时抛出
	 */
	public boolean get(int bitIndex) {
		if (bitIndex < 0)
			throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);

		int _byteIndex = byteIndex(bitIndex);
		return (_byteIndex < bytes.length)
				&& ((bytes[_byteIndex] & indexMarkInByte(bitIndex)) != 0);
	}

	/**
	 * 将指定索引上的位置为给定值
	 *
	 * @param bitIndex
	 *            若该索引上越界，则该操作没有意义
	 * @param value
	 * @exception IndexOutOfBoundsException
	 *                指定索引为负时抛出
	 */
	public void set(int bitIndex, boolean value) {
		if (value) {
			set(bitIndex);
		} else {
			clear(bitIndex);
		}
	}

	/**
	 * 将指定索引上位的值置1
	 *
	 * @param bitIndex
	 *            若该索引上越界，则该操作没有意义
	 * @exception IndexOutOfBoundsException
	 *                指定索引为负时抛出
	 */
	public void set(int bitIndex) {
		if (bitIndex < 0)
			throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
		int _byteIndex = byteIndex(bitIndex);
		if (_byteIndex < bytes.length) {
			bytes[_byteIndex] |= indexMarkInByte(bitIndex);
		}
	}

	/**
	 * 将指定索引上位的值置0
	 *
	 * @param bitIndex
	 *            若该索引上越界，则该操作没有意义
	 * @exception IndexOutOfBoundsException
	 *                指定索引为负时抛出
	 */
	public void clear(int bitIndex) {
		if (bitIndex < 0)
			throw new IndexOutOfBoundsException("bitIndex < 0: " + bitIndex);
		int _byteIndex = byteIndex(bitIndex);
		if (_byteIndex < bytes.length) {
			bytes[_byteIndex] &= ~indexMarkInByte(bitIndex);
		}
	}

	/**
	 * 返回可表示的位大小
	 *
	 * @return
	 */
	public int size() {
		return bytes.length << ADDRESS_BITS_PER_BYTE;
	}

	/**
	 * 获得当前表示位的字节数组
	 *
	 * @return
	 */
	public byte[] getByteArray() {
		return bytes;
	}
}
