package com.snowcattle.game.common.util;

import java.util.Iterator;
import java.util.List;

/**
 *
 * 一个均匀分布的随机数序列，一般用于游戏中物品的掉落
 * 例如
 * A 2%
 * B 3%
 * c %6
 * ...
 *
 * 用法
 * 初始化阶段
 * UniformRandomChooser<Item> urc = new UniformRandomChooser<Item>;
 * urc.addObject(0.1, item1);
 * urc.addObject(0.2, item2);
 * urc.addObject(0.3, item3);
 * urc.addObject(0.4, item4);
 * urc.compile();
 *
 * 使用阶段
 * urc.getObject(0.576);(返回item3)
 *
 * @author jackflit
 *
 */
public class UniformRandomChooser<T> {

	private List<Entry<T>> objects;
	private float maxup;

	public UniformRandomChooser(){
		objects = new java.util.LinkedList<Entry<T>>();
	}

	/**
	 * 添加物品T，出现概率rate
	 * @param rate
	 * @param obj
	 */
	public void addObject(float rate, T obj){
		Entry<T> entry = new Entry<T>();
		entry.rate = rate;
		entry.obj = obj;
		objects.add(entry);
	}

	public void compile(){
		float sum = 0;
		// 计算sum
		Iterator<Entry<T>> iter = objects.iterator();
		while(iter.hasNext()){
			Entry<T> entry = iter.next();
			sum+=entry.rate;
			entry.up = sum;
		}

		// 归一化
		iter = objects.iterator();
		while(iter.hasNext()){
			Entry<T> entry = iter.next();
			entry.up = entry.up/sum;
			maxup = entry.up;
		}
	}

	/**
	 * 根据概率返回物品
	 * rate如果在[0, 1.0]之外会按照边界值处理
	 * 一般来说，只要物品列表不为空，就不会返回空
	 *
	 * @param rate
	 * @return
	 */
	public T getObject(float rate){
		if(rate < 0){
			rate = 0;
		}
		if(rate > maxup){
			rate = maxup;
		}
		Iterator<Entry<T>> iter = objects.iterator();
		while(iter.hasNext()){
			Entry<T> entry = iter.next();
			if(rate <= entry.up){
				return entry.obj;
			}
		}
		return null;
	}

	private static class Entry<T>{
		public float rate;	// 原始掉落率
		public float up;
		public T obj;
	}

}
