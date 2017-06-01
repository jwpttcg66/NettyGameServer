package com.snowcattle.game.common.util;

import java.lang.reflect.Array;
import java.util.BitSet;

/**
 * 泛型属性数组
 *
 *
 */
public class GenericPropertyArray<T> {

	private final T[] values;
	private final BitSet bitSet;

	@SuppressWarnings("unchecked")
	public GenericPropertyArray(Class<T> clazz,int size) {
		values =(T[]) Array.newInstance(clazz,size);
		bitSet = new BitSet(size);
	}

	/**
	 * 从指定的数值对象src拷贝数据到本实例
	 *
	 * @param src
	 *            数据的来源
	 */
	public void copyFrom(GenericPropertyArray<T> src) {
		System.arraycopy(src.values, 0, values, 0, values.length);
		this.bitSet.set(0, values.length, true);
	}

	public boolean isChanged() {
		return !this.bitSet.isEmpty();
	}

	/**
	 * 指定的索引是否有修改
	 *
	 * @param index
	 * @return true,有修改;false,无修改
	 */
	public boolean isChanged(int index) {
		return this.bitSet.get(index);
	}

	public void resetChanged() {
		this.bitSet.clear();
	}
	
	public void change(){
		for(int i=0; i<this.values.length; i++){
			if(this.values[i] != null){
				this.bitSet.set(i);
			}
		}
	}
	public T get(int index)
	{
		return values[index];
	}

	public void set(int index, T val) {
		T _old = this.values[index];
		boolean _changed = false;
		if (_old != null) {
			if (!_old.equals(val)) {
				_changed = true;
			}
		} else if (val != null) {
			_changed = true;
		}
		if (_changed) {
			this.values[index] = val;
			this.bitSet.set(index);
		}
	}

	public int size() {
		return values.length;
	}

	/**
	 * 将当前的修改标识填充到toBitSet中
	 *
	 * @param toBitSet
	 * @return false,如果当前的属性没有修改;true,当前的属性有修改,并且已经将对应的值设置到toBitSet中
	 */
	public boolean fillChangedBit(final BitSet toBitSet) {
		if (this.bitSet.isEmpty()) {
			return false;
		} else {
			toBitSet.or(this.bitSet);
			return true;
		}
	}
	/**
	 * 取得被修改过的的属性索引及其对应的值
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public KeyValuePair<Integer,T>[] getChanged() {
		KeyValuePair<Integer,T>[] _changed=new KeyValuePair[bitSet.cardinality()];
		for (int i = bitSet.nextSetBit(0), j = 0; i >= 0; i = bitSet.nextSetBit(i + 1), j++) {
			_changed[j]=new KeyValuePair<Integer,T>(i,values[i]);
		}
		return _changed;
	}

	public String toString() {
		return StringUtils.obj2String(this, null);
	}


}
