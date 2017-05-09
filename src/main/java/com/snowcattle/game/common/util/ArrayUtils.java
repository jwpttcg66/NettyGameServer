package com.snowcattle.game.common.util;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ArrayUtils {

	public static int[] intList2Array(List<Integer> list) {
		if (list != null) {
			int[] ary = new int[list.size()];
			for (int i = 0; i < list.size(); i++) {
				ary[i] = list.get(i);
			}
			return ary;
		}
		return null;
	}

	public static int[] intSet2Array(Set<Integer> set) {
		if (set != null) {
			int i = 0;
			int[] ary = new int[set.size()];
			for (Integer integer : set) {
				ary[i] = integer;
				i++;
			}
			return ary;
		}
		return null;
	}
	
	public static Long[] LongSet2Array(Set<String> set) {
		if (set != null) {
			int i = 0;
			Long[] ary = new Long[set.size()];
			for (String key : set) {
				ary[i] = Long.parseLong(key);
				i++;
			}
			return ary;
		}
		return null;
	}

	/**
	 * 创建一个二维数组
	 *
	 * @param <T>
	 *            数组单元的类型
	 * @param rows
	 *            数组的第一维长度
	 * @param cols
	 *            数组的第二位长度
	 * @param clazz
	 *            数组单元的类型
	 * @return 如果类型实例化失败,则数组里的单元为null
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[][] createTwoDimensionArray(int rows, int cols,
			Class<T> clazz) {
		T[][] arr = (T[][]) Array.newInstance(clazz, rows, cols);
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < cols; x++) {
				try {
					arr[y][x] = clazz.newInstance();
				} catch (Exception e) {
					arr[y][x] = null;
				}
			}
		}
		return arr;
	}


	/**
	 * 获取数组
	 * @param id
	 * @return
	 */
	public static List<String> getList(Serializable... id) {
		List<String> list = new ArrayList<String>();
		for(Serializable key: id){
			list.add(String.valueOf(key));
		}
		return list;
	}
	
	
	/**
	 * 获取数组
	 * @param ids
	 * @return
	 */
	public static Set<Integer> getIntegerSet(List<String> ids) {
		Set<Integer> list = new HashSet<Integer>();
		for(String key: ids){
			list.add(Integer.parseInt(key));
		}
		return list;
	}
}
