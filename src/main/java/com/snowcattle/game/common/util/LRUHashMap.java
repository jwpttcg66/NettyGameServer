package com.snowcattle.game.common.util;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * 提供LRU算法的Map实现
 * 
 * 
 * @param <K>
 * @param <V>
 */
public class LRUHashMap<K, V> extends LinkedHashMap<K, V> {
	private static final long serialVersionUID = 1L;
	/** 最大个数 */
	private final int maxSize;
	/** 淘汰策略 */
	private transient final EvictPolicy<V> evictPolicy;
	/** 命中次数 */
	private long hitCount = 0;
	/** 未命中次数 */
	private long missCount = 0;

	/** 默认的淘汰策略:总是淘汰 */
	private transient final EvictPolicy<V> DEFAULT = new EvictPolicy<V>() {
		@Override
		public boolean evict(V value) {
			return true;
		}
	};

	public LRUHashMap(int maxSize, EvictPolicy<V> evictPolicy) {
		super(10, 0.75f, true);
		if (maxSize <= 0) {
			throw new IllegalArgumentException("The max size must be >0");
		}
		if (evictPolicy != null) {
			this.evictPolicy = evictPolicy;
		} else {
			this.evictPolicy = DEFAULT;
		}
		this.maxSize = maxSize;
	}

	@Override
	public V get(Object key) {
		final V _value = super.get(key);
		if (_value != null) {
			++hitCount;
		} else {
			++missCount;
		}
		return _value;
	}

	/**
	 * @return 命中的次数
	 */
	public long getHitCount() {
		return hitCount;
	}

	/**
	 * @return 未命中的次数
	 */
	public long getMissCount() {
		return missCount;
	}

	/**
	 * 取得命中率
	 * 
	 * @return
	 */
	public float getHitRate() {
		final long _hitCount = this.hitCount;
		final long _missCount = this.missCount;
		final long _total = _hitCount + _missCount;
		if (_total == 0) {
			return 0;
		} else {
			final double _hitCountD = _hitCount;
			return (float) (_hitCountD / _total);
		}
	}

	/**
	 * 获取LRUHashMap的统计信息
	 * 
	 * @return
	 */
	public String getStatistics() {
		return "cache size:" + size() + "/" + this.maxSize + ",hit:" + getHitCount() + ",miss:" + getMissCount() + ",hitRate:"
				+ (int) (getHitRate() * 100) + "%";
	}

	@Override
	protected boolean removeEldestEntry(Entry<K, V> eldest) {
		if (this.size() <= maxSize) {
			return false;
		}
		return this.evictPolicy.evict(eldest.getValue());
	}

	public static interface EvictPolicy<V> {
		public boolean evict(V value);
	}
}