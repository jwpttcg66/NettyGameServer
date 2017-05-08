package com.snowcattle.game.common.util;

import java.util.BitSet;

/**
 * 基本数组实现的数值属性值对象
 *
 *
 */
public final class IntNumberPropertyArray implements Cloneable {
	/** 保存数值 */
	private final int[] values;
	/** 数值是否修改的标识 */
	private final BitSet bitSet;

	/**
	 * 创建一个有size个数据的数值属性集合
	 *
	 * @param size
	 *            数据的个数
	 */
	public IntNumberPropertyArray(int size) {
		values = new int[size];
		bitSet = new BitSet(size);
	}

	public IntNumberPropertyArray(IntNumberPropertyArray set) {
		values = new int[set.size()];
		bitSet = new BitSet(values.length);
		System.arraycopy(set.values, 0, values, 0, values.length);
		this.bitSet.set(0, values.length, true);
	}

	/**
	 * 从指定的数值对象src拷贝数据到本实例
	 *
	 * @param src
	 *            数据的来源
	 */
	public void copyFrom(IntNumberPropertyArray src) {
		System.arraycopy(src.values, 0, values, 0, values.length);
		this.bitSet.set(0, values.length, true);
	}

	/**
	 * 设置index对应的值为value
	 *
	 * @param index
	 * @param value
	 * @return 返回值是是否确实被修改 true,修改;false,未修改
	 * @throws ArrayIndexOutOfBoundsException
	 *             如果index<0 或者 index>=size时会抛出此异常
	 */
	public boolean set(int index, int value) {
		int _o = values[index];
		if (_o != value) {
			values[index] = value;
			bitSet.set(index);
			return true;
		}
		return false;
	}

	/**
	 * 取得index对应的int值
	 *
	 * @param index
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 *             如果index<0 或者 index>=size时会抛出此异常
	 */
	public int get(int index) {
		return values[index];
	}

	/**
	 * 增加index对应的int值
	 *
	 * @param index
	 *            属性的索引
	 * @param value
	 *            将要增加的值
	 * @return 增加后的值
	 */
	public int add(int index, int value) {
		int _o = values[index];
		int _n = _o + value;
		if (_o != _n) {
			values[index] = _n;
			bitSet.set(index);
		}
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
	 * 判定指定位是否修改
	 *
	 * @param index
	 * @return
	 */
	public boolean isChanged(int index){
		return this.bitSet.get(index);
	}

	/**
	 * 重置修改,将所有的修改标识清空
	 */
	public void resetChanged() {
		this.bitSet.clear();
	}

	/**
	 * 取得被修改过的的属性索引:优化修改过的索引及值,返回统一的Object
	 *
	 * @return
	 */
	public Object[] getChanged() {
		int[] _indexes = new int[bitSet.cardinality()];
		int[] _values = new int[bitSet.cardinality()];
		Object[] changed = new Object[2];
		changed[0] = _indexes;
		changed[1] = _values;
		for (int i = bitSet.nextSetBit(0), j = 0; i >= 0; i = bitSet.nextSetBit(i + 1), j++) {
			_indexes[j] = i;
			_values[j] = this.values[i];
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
			values[i] = 0;
		}
		this.bitSet.clear();
	}

	/**
	 * 计算该数据对象的所有数值的和
	 *
	 * @return
	 */
	public int sum() {
		int _sum = 0;
		for (int i = 0; i < this.values.length; i++) {
			_sum += this.values[i];
		}
		return _sum;
	}

	/**
	 * 计算由指定的索引数组标识的属性数值的和
	 *
	 * @param indexs
	 *            被计算的属性的索引数组
	 * @return
	 */
	public int sum(int[] indexs) {
		int _sum = 0;
		for (int i = 0; i < indexs.length; i++) {
			_sum += this.values[indexs[i]];
		}
		return _sum;
	}

	/**
	 * 计算除了指定的索引数组标识的以外的属性数值的和
	 *
	 * @param exceptIndexs
	 *            被排除的属性的索引数组
	 * @return
	 */
	public int sumExcept(final int[] exceptIndexs) {
		int _sum = 0;
		for (int i = 0; i < values.length; i++) {
			_sum += this.values[i];
		}
		for (int i = 0; i < exceptIndexs.length; i++) {
			_sum -= this.values[exceptIndexs[i]];
		}
		return _sum;
	}

	public String toString() {
		return StringUtils.obj2String(this, null);
	}

	@Override
	public IntNumberPropertyArray clone() {
		IntNumberPropertyArray newGuy = new IntNumberPropertyArray(this);
		return newGuy;
	}


	public KeyValuePair<Integer, Integer>[] getIndexValuePairs() {
		KeyValuePair<Integer, Integer>[] indexValuePairs = KeyValuePair.newKeyValuePairArray(values.length);
		for (int i = 0; i < indexValuePairs.length; i++) {
			indexValuePairs[i] = new KeyValuePair<Integer, Integer>(Integer
					.valueOf(i), values[i]);
		}
		return indexValuePairs;
	}

}
