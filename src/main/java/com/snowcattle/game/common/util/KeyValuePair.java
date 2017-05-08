package com.snowcattle.game.common.util;

import java.lang.reflect.Array;

/**
 *
 * 二元组
 *
 */
public class KeyValuePair<K,V> {

	/**key值*/
    K key;

    /**value值*/
    V value;

    public KeyValuePair()
    {

    }

    public KeyValuePair(K k,V v)
    {
    	 this.key=k;
    	 this.value=v;
    }

	public K getKey() {
		return key;
	}

	public V getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyValuePair<K,V> other = (KeyValuePair<K,V>) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public void setValue(V value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "KeyValuePair [key=" + key + ", value=" + value + "]";
	}

	@SuppressWarnings("unchecked")
	public static <K, V> KeyValuePair<K, V>[] newKeyValuePairArray(int size) {
		return (KeyValuePair<K, V>[])Array.newInstance(KeyValuePair.class, size);
	}
}