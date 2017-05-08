package com.snowcattle.game.common.util;

import java.util.BitSet;

/**
 * 基于数组实现的字符串属性值对象
 *
 *
 */
public final class StringPropertyArray implements Cloneable {
	/** 保存字符串 */
	private final String[] values;
	/** 数值是否修改的标识 */
	private final BitSet bitSet;

	/**
	 * 创建一个有size个数据的数值属性集合
	 *
	 * @param size
	 *            数据的个数
	 */
	public StringPropertyArray(int size) {
		values = new String[size];
		bitSet = new BitSet(size);
	}

	public StringPropertyArray(StringPropertyArray set) {
		values = new String[set.size()];
		bitSet = new BitSet(values.length);
		System.arraycopy(set.values, 0, values, 0, values.length);
	}

	/**
	 * 从指定的数值对象src拷贝数据到本实例
	 *
	 * @param src
	 *            数据的来源
	 */
	public void copyFrom(StringPropertyArray src) {
		System.arraycopy(src.values, 0, values, 0, values.length);
	}

	/**
	 * 设置index对应的值为value
	 *
	 * @param index
	 * @param value
	 * @throws ArrayIndexOutOfBoundsException
	 *             如果index<0 或者 index>=size时会抛出此异常
	 */
	public void setString(int index, String value) {
		String _o = values[index];
		if (!StringUtils.isEquals(_o, value)) {
			values[index] = value;
			bitSet.set(index);
		}
	}

	/**
	 * 取得index对应的String值
	 *
	 * @param index
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 *             如果index<0 或者 index>=size时会抛出此异常
	 */
	public String getString(int index) {
		return values[index];
	}

	/**
	 * 是否有修改
	 *
	 * @return
	 */
	public boolean isChanged() {
		return !this.bitSet.isEmpty();
	}

	/**
	 * 重置修改,将所有的修改标识清空
	 */
	public void resetChanged() {
		this.bitSet.clear();
	}

	/**
	 * 取得被修改过的的属性索引及其对应的值
	 *
	 * @return 一个2维数组,第二维的长度为2,changed[][0]标识属性的int类型的索引,changed[][1]标识属性的值
	 */
	public Object[][] getChanged() {
		Object[][] changed = new Object[bitSet.cardinality()][2];
		for (int i = bitSet.nextSetBit(0), j = 0; i >= 0; i = bitSet.nextSetBit(i + 1), j++) {
			changed[j][0] = i;
			changed[j][1] = this.values[i];
		}
		return changed;
	}

	/**
	 * 取得属性的个数
	 *
	 * @return
	 */
	public int size() {
		return this.values.length;
	}

	/**
	 * 清空状态,将values重置为0;把bitSet同时重置
	 */
	public void clear() {
		for (int i = 0; i < values.length; i++) {
			values[i] = null;
		}
		this.bitSet.clear();
	}

	public String toString() {
		return StringUtils.obj2String(this, null);
	}

	@Override
	public StringPropertyArray clone() {
		StringPropertyArray newGuy = new StringPropertyArray(this);
		return newGuy;
	}
}
