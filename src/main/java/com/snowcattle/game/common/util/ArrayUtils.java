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
	 * 进行数组比较,每次比较会将结果回调给不同的函数.如果数组中存在null的元素, 则跳过此元素进行下一个的比较,且不会调用
	 * <code>compCallback</code>
	 *
	 * @param <T>
	 *            数组的类型,必须实现<code>Comparable</code>接口
	 * @param arr1
	 *            第一个数组
	 * @param arr2
	 *            第二个数组
	 * @param compCallback
	 *            比较后的回调函数,第一个参数是比较值comp,见<code>Compareble.compareTo</code>方法.
	 *            第二个参数根据comp的值不同,当comp小于0时为较小的值,当comp等于0时为相同的值,当comp大于0时为较大的值
	 */
	public static <T extends Comparable<T>> void compare(T[] arr1, T[] arr2,
			Functions.Function2<Integer, T> compCallback) {
		int index1 = 0;
		int index2 = 0;
		while (index1 < arr1.length && index2 < arr2.length) {
			T obj1 = arr1[index1];
			T obj2 = arr2[index2];
			if (obj1 == null) {
				index1++;
				continue;
			}
			if (obj2 == null) {
				index2++;
				continue;
			}
			int comp = obj1.compareTo(obj2);
			if (comp < 0) {
				compCallback.apply(comp, obj1);
				index1++;
			} else if (comp == 0) {
				compCallback.apply(comp, obj1);
				index1++;
				index2++;
			} else {
				compCallback.apply(comp, obj2);
				index2++;
			}
		}
		// 修正从单元格0（第一行第一列）移动到单元格9（第二行第一列）时比较结果不准确的问题
		// modified by zhangwh 2010/3/29
		while (index1 < arr1.length) {
			if (arr1[index1] != null)
				compCallback.apply(-1, arr1[index1]);
			index1++;
		}
		while (index2 < arr2.length) {
			if (arr2[index2] != null)
				compCallback.apply(1, arr2[index2]);
			index2++;
		}
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
	 * @param id
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
