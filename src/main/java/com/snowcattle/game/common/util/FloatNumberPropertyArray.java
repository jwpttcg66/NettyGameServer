package com.snowcattle.game.common.util;

import java.util.BitSet;

/**
 * 基本数组实现的数值属性值对象
 *
 *
 */
public final class FloatNumberPropertyArray implements Cloneable {
	/** 保存数值 */
	private final float[] values;
	/** 数值是否修改的标识 */
	private final BitSet bitSet;

	/**
	 * 创建一个有size个数据的数值属性集合
	 *
	 * @param size
	 *            数据的个数
	 */
	public FloatNumberPropertyArray(int size) {
		values = new float[size];
		bitSet = new BitSet(size);
	}

	public FloatNumberPropertyArray(FloatNumberPropertyArray set) {
		values = new float[set.size()];
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
	public void copyFrom(FloatNumberPropertyArray src) {
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
	public boolean set(int index, float value) {
		float _o = values[index];
		if (!MathUtils.floatEquals(_o, value)) {
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
	public float get(int index) {
		return values[index];
	}

	/**
	 * 增加index对应的值
	 *
	 * @param index
	 *            属性的索引
	 * @param value
	 *            将要增加的值
	 * @return 增加后的值
	 */
	public float add(int index, float value) {
		float _o = values[index];
		if (!MathUtils.floatEquals(0.0f, value)) {
			float _n = _o + value;
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
	 * 取得被修改过的的属性索引
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public KeyValuePair<Integer, Float>[] getChanged() {
		KeyValuePair<Integer, Float>[] changedPairs = new KeyValuePair[bitSet
				.cardinality()];
		for (int i = bitSet.nextSetBit(0), j = 0; i >= 0; i = bitSet
				.nextSetBit(i + 1), j++) {
			changedPairs[i] = new KeyValuePair(Integer.valueOf(i), Float
					.valueOf(this.values[i]));
		}
		return changedPairs;
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
	 * 清空状态,将values重置为0;将所有属性都设置为changed
	 */
	public void clear() {
		for (int i = 0; i < values.length; i++) {
			if (!MathUtils.floatEquals(values[i], 0.0f)) {
				this.bitSet.set(i);
			}
			values[i] = 0.0f;
		}
	}

	/**
	 * 计算该数据对象的所有数值的和
	 *
	 * @return
	 */
	public float sum() {
		float _sum = 0;
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
	public float sum(int[] indexs) {
		float _sum = 0;
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
	public float sumExcept(final int[] exceptIndexs) {
		float _sum = 0;
		for (int i = 0; i < values.length; i++) {
			_sum += this.values[i];
		}
		for (int i = 0; i < exceptIndexs.length; i++) {
			_sum -= this.values[exceptIndexs[i]];
		}
		return _sum;
	}

	@Override
	public String toString() {
		return StringUtils.obj2String(this, null);
	}

	@Override
	public FloatNumberPropertyArray clone() {
		FloatNumberPropertyArray newGuy = new FloatNumberPropertyArray(this);
		return newGuy;
	}

	public KeyValuePair<Integer, Float>[] getIndexValuePairs() {
		KeyValuePair<Integer, Float>[] indexValuePairs = KeyValuePair.newKeyValuePairArray(values.length);
		for (int i = 0; i < indexValuePairs.length; i++) {
			indexValuePairs[i] = new KeyValuePair<Integer, Float>(Integer.valueOf(i), Float.valueOf(values[i]));
		}
		return indexValuePairs;
	}

}
