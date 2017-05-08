package com.snowcattle.game.common.util;

import java.util.BitSet;

/**
 * 基于数组实现的对象属性值对象
 *
 *
 */
public class PropertyArray {
	private final Object[] values;
	private final BitSet bitSet;

	public PropertyArray(int size) {
		values = new Object[size];
		bitSet = new BitSet(size);
	}

	/**
	 * 从指定的数值对象src拷贝数据到本实例
	 *
	 * @param src
	 *            数据的来源
	 */
	public void copyFrom(PropertyArray src) {
		System.arraycopy(src.values, 0, values, 0, values.length);
		this.bitSet.set(0, values.length, true);
	}

	public void setInt(int index, int val) {
		this.set(index, Integer.valueOf(val));
	}

	public int getInt(int index) {
		Integer _val = (Integer) this.values[index];
		if (_val == null) {
			return 0;
		}
		return _val.intValue();
	}

	public void setLong(int index, long val) {
		this.set(index, Long.valueOf(val));
	}

	public long getLong(int index) {
		Long _val = (Long) this.values[index];
		if (_val == null) {
			return 0;
		}
		return _val.longValue();
	}

	public void setShort(int index, short val) {
		this.set(index, Short.valueOf(val));
	}

	public short getShort(int index) {
		Short _val = (Short) this.values[index];
		if (_val == null) {
			return 0;
		}
		return _val.shortValue();
	}

	public void setByte(int index, byte val) {
		this.set(index, Byte.valueOf(val));
	}

	public byte getByte(int index) {
		Byte _val = (Byte) this.values[index];
		if (_val == null) {
			return 0;
		}
		return _val.byteValue();
	}

	public void setString(int index, String val) {
		this.set(index, val);
	}

	public String getString(int index) {
		return (String) this.values[index];
	}

	public void setObject(int index, Object val) {
		this.set(index, val);
	}

	public Object getObject(int index) {
		return this.values[index];
	}

	public boolean isChanged() {
		return !this.bitSet.isEmpty();
	}

	public void resetChanged() {
		this.bitSet.clear();
	}
	
	public void change(){
		for(int i=0; i< this.values.length; i++){
			if(this.values[i] != null){
				this.bitSet.set(i);
			}
		}
	}
	
	public void set(int index, Object val) {
		Object _old = this.values[index];
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

	/**
	 * 取得index对应的int值
	 *
	 * @param index
	 * @return
	 * @throws ArrayIndexOutOfBoundsException
	 *             如果index<0 或者 index>=size时会抛出此异常
	 */
	public Object get(int index) {
		return values[index];
	}

	public int size() {
		return values.length;
	}

	/**
	 * 取得被修改过的的属性索引及其对应的值
	 *
	 * @return
	 */
	public Object[] getChanged() {
		int[] _indexes = new int[bitSet.cardinality()];
		Object[] _values = new Object[bitSet.cardinality()];
		Object[] _changed = new Object[2];
		_changed[0] = _indexes;
		_changed[1] = _values;
		for (int i = bitSet.nextSetBit(0), j = 0; i >= 0; i = bitSet.nextSetBit(i + 1), j++) {
			_indexes[j] = i;
			_values[j] = this.values[i];
		}
		return _changed;
	}

	@Override
	public String toString() {
		return StringUtils.obj2String(this, null);
	}

}
