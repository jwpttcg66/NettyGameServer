package com.snowcattle.game.common.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 按原类型保存的属性集合
 *
 *
 */
public final class PropertyRawSet<K, V> implements Cloneable {

	private final Map<K, V> p;

	public PropertyRawSet() {
		p = new HashMap<K, V>(0);
	}

	public PropertyRawSet(PropertyRawSet<K, V> pset) {
		p = new HashMap<K, V>(pset.p);
	}

	public PropertyRawSet(Map<K,V> pset){
		p = new HashMap<K, V>(pset);
	}

	public void set(K key, V val) {
		p.put(key, val);
	}

	public V get(K key) {
		return p.get(key);
	}

	public int getInt(K key, int defVal) {
		Number val = (Number) p.get(key);
		if (val == null) {
			return defVal;
		}
		return val.intValue();
	}

	public long getLong(K key, long defVal) {
		Number val = (Number) p.get(key);
		if (val == null) {
			return defVal;
		}
		return val.longValue();
	}

	public float getFloat(K key, float defVal) {
		Number val = (Number) p.get(key);
		if (val == null) {
			return defVal;
		}
		return val.floatValue();

	}

	public String getString(K key, String defVal) {
		V ret = p.get(key);
		if (ret == null) {
			return defVal;
		} else {
			return ret.toString();
		}
	}

	public boolean contains(K key) {
		return p.containsKey(key);
	}

	public V remove(K key) {
		return p.remove(key);
	}

	public Map<K, V> getProperties() {
		return Collections.unmodifiableMap(p);
	}

	public int size() {
		return p.size();
	}

	@Override
	public PropertyRawSet<K, V> clone() {
		PropertyRawSet<K, V> newGuy = new PropertyRawSet<K, V>(this);
		return newGuy;
	}

	public String toString() {
		return StringUtils.obj2String(this, null);
	}
}
