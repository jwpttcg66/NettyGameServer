package com.snowcattle.game.common.util;


public class Range<T extends Comparable<T>> {

	private T min;

	private T max;

	private Range(){
	}

	public Range(T min, T max){
		this();
		setMin(min);
		setMax(max);
	}

	public T getMin() {
		return min;
	}

	public void setMin(T min) {
		this.min = min;
	}

	public T getMax() {
		return max;
	}

	public void setMax(T max) {
		this.max = max;
	}


	/**
	 * 若value -> [min, max)返回true
	 */
	public boolean contains(T value){
		return value.compareTo(min) >= 0 && value.compareTo(max) < 0;
	}

}
