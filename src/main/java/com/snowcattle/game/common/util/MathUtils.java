package com.snowcattle.game.common.util;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 从LZR拷贝过来
 *
 */
public class MathUtils {

	// public static final float EPSILON = 0.00001f;//10-6对于double转float容易误差
	// 宠物点数校验时。 modified by sxf 090121
	public static final float EPSILON = 0.00004f;// 再把误差调得大一点,现在这样,在150级时百万次检查大概会出现8次超出误差值

	public static Random random = new Random();
	/**
	 * 返回>=low, <=hi的整数随机数，均匀分布
	 *
	 * @param low
	 * @param hi
	 * @return
	 */
	public static int random(int low, int hi) {
		return (int) (low + (hi - low + 0.9) * Math.random());
	}


	/**
	 * 返回>=low, <hi的浮点随机数，均匀分布
	 *
	 * @param low
	 * @param hi
	 * @return
	 */
	public static float random(float low, float hi) {
		return (float) (low + (hi - low) * Math.random());
	}

	/**
	 * 非均匀分布的数组，返回命中数组元素的索引 全未命中返回-1
	 *
	 * @param rateArray
	 *            数组中各元素的值为该元素被命中的权重
	 * @return 命中的数组元素的索引
	 */
	public static int random(Integer[] rateArray) {
		int[] rateArrayInt = new int[rateArray.length];
		for (int i = 0; i < rateArray.length; i++) {
			rateArrayInt[i] = rateArray[i];
		}
		return MathUtils.random(rateArrayInt);
	}

	/**
	 * 非均匀分布的数组，返回命中数组元素的索引 全未命中返回-1
	 *
	 * @param rateArray
	 *            数组中各元素的值为该元素被命中的权重
	 * @return 命中的数组元素的索引
	 */
	public static int random(int[] rateArray) {
		if (null == rateArray) {
			throw new IllegalArgumentException(
					"The random array must not be null!");
		}
		int arrayLength = rateArray.length;
		if (arrayLength == 0) {
			throw new IllegalArgumentException(
					"The random array's length must not be zero!");
		}
		// 依次累加的和
		int rateSum = 0;
		// 从头开始 依次累加之后的各个元素和 的临时数组
		int[] rateSumArray = new int[arrayLength];

		for (int i = 0; i < arrayLength; i++) {

			if (rateArray[i] < 0) {
				throw new IllegalArgumentException(
						"The array's element must not be equal or greater than zero!");
			}
			rateSum += rateArray[i];
			rateSumArray[i] = rateSum;
		}
		if (rateSum <= 0) {
			// 所有概率都为零，必然没有选中的元素，返回无效索引:-1
			return -1;
		}

		int randomInt = MathUtils.random(1, rateSum);
		int bingoIndex = -1;
		for (int i = 0; i < arrayLength; i++) {
			if (randomInt <= rateSumArray[i]) {
				bingoIndex = i;
				break;
			}
		}
		if (bingoIndex == -1) {
			throw new IllegalStateException("Cannot find out bingo index!");
		}
		return bingoIndex;
	}

	/**
	 * 返回是否满足概率值。
	 *
	 * @param shakeNum
	 *            float 概率值 0.0---1.0
	 * @return 比如某操作有２０％的概率，shakeNum=0.2 如果返回true表明概率满足。
	 */
	public static boolean shake(float shakeNum) {
		if (shakeNum >= 1) {
			return true;
		}
		if (shakeNum <= 0) {
			return false;
		}

		double a = Math.random();
		return a < shakeNum;
	}

	/**
	 * 从一个枚举中随机一个值
	 *
	 * @param enumClass
	 *            枚举类型
	 * @return 随机出的一个枚举值
	 */
	public static <T extends Enum<T>> T random(Class<T> enumClass) {
		T[] elements = enumClass.getEnumConstants();
		return elements[random(0, elements.length - 1)];
	}

	/**
	 * 注意如有需要，请使用@see {@link AIUtils#luckyDraw(float[])}
	 *
	 * 抽奖 按照rateAry[i]要求的概率 返回 i； 计算物品必然掉落的情况 适用
	 *
	 * @param rateAry
	 *            概率数组 要求 数组元素 和为1
	 * @return
	 */
	@Deprecated
	public static int luckyDraw(float[] rateAry) {
		if (rateAry == null) {
			return -1;// modified by sxf 090310
		}

		int[] balls = new int[100];
		int pt = 0;
		for (int i = 0; i < rateAry.length; i++) {
			int mulRate = (int) (rateAry[i] * 100);
			for (int j = 0; j < mulRate; j++) {
				try {
					balls[pt] = i;
				} catch (ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
					return rateAry.length - 1;
				}
				pt++;
			}
		}
		return balls[random(0, 99)];
	}

	public static int parseInt(Object input, int defaultValue) {
		if (input == null)
			return defaultValue;
		try {
			return Integer.parseInt(input.toString());
		} catch (Exception e) {
		}
		return defaultValue;
	}

	public static int compareFloat(float f1, float f2) {
		float delta = f1 - f2;
		if (Math.abs(delta) > EPSILON) {
			if (delta > 0) {
				return 1; // f1> f2
			} else if (delta < 0) {
				return -1;// f1<f2
			}
		}
		return 0;// f1==f2
	}

	public static int compareToByDay(Calendar dayone, Calendar daytwo) {
		if (dayone.get(Calendar.YEAR) > daytwo.get(Calendar.YEAR)) {
			return 1;
		} else if (dayone.get(Calendar.YEAR) < daytwo.get(Calendar.YEAR)) {
			return -1;
		} else {
			if (dayone.get(Calendar.MONTH) > daytwo.get(Calendar.MONTH)) {
				return 1;
			} else if (dayone.get(Calendar.MONTH) < daytwo.get(Calendar.MONTH)) {
				return -1;
			} else {
				if (dayone.get(Calendar.DAY_OF_MONTH) > daytwo
						.get(Calendar.DAY_OF_MONTH)) {
					return 1;
				} else if (dayone.get(Calendar.DAY_OF_MONTH) < daytwo
						.get(Calendar.DAY_OF_MONTH)) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

	/**
	 * 计算两个日期间相差的天数(按24小时算)
	 *
	 * @param enddate
	 * @param begindate
	 * @return
	 */
	public static int getIntervalDays(Date enddate, Date begindate) {
		long millisecond = enddate.getTime() - begindate.getTime();
		int day = (int) (millisecond / 24l / 60l / 60l / 1000l);
		return day;
	}

	/**
	 * 计算两个日期间相差的天数(按24小时算)
	 *
	 * @param enddate
	 * @param begindate
	 * @return
	 */
	public static int getIntervalDays(long enddate, long begindate) {
		long millisecond = enddate - begindate;
		int day = (int) (millisecond / 24l / 60l / 60l / 1000l);
		return day;
	}

	/**
	 * 计算两个日期间相差的分钟数
	 *
	 * @param enddate
	 * @param begindate
	 * @return
	 */
	public static int getIntervalMinutes(Date enddate, Date begindate) {
		long millisecond = enddate.getTime() - begindate.getTime();
		int minute = (int) (millisecond / 60l / 1000l);
		return minute;
	}

	/**
	 * 计算两个日期间相差的分钟数
	 *
	 * @param enddate
	 * @param begindate
	 * @return
	 */
	public static int getIntervalMinutes(long enddate, long begindate) {
		int minute = (int) ((Math.abs(enddate - begindate)) / 60l / 1000l);
		return minute;
	}

	/**
	 * 限置为 >=min <max的值
	 *
	 * @param original
	 * @param min
	 * @param max
	 * @return
	 */
	public static int setBetween(int original, int min, int max) {
		if (original >= max) {
			original = max - 1;
		}
		if (original < min) {
			original = min;
		}
		return original;
	}

	/**
	 * 限置为 >=min <max的值
	 *
	 * @param original
	 * @param min
	 * @param max
	 * @return
	 */
	public static long setBetween(long original, long min, long max) {
		if (original >= max) {
			original = max - 1;
		}
		if (original < min) {
			original = min;
		}
		return original;
	}

	/**
	 * @param ary1
	 * @param ary2
	 * @return ary1 >= ary2 true else false
	 */
	public static boolean compareArrays(int[] ary1, int[] ary2) {
		if (ary1 != null && ary2 != null) {
			if (ary1.length == ary2.length) {
				for (int i = 0; i < ary1.length; i++) {
					if (ary1[i] < ary2[i]) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public static int float2Int(float f) {
		return (int) (f + 0.5f);
	}

	/**
	 * 获取两数相除的结果,精确到小数
	 *
	 * @param num
	 * @param deno
	 * @return
	 */
	public static float doDiv(int numerator, int denominator) {
		if (denominator != 0) {
			return numerator / (denominator + 0.0f);
		}
		return 0f;
	}

	public static float doDiv(float numerator, float denominator) {
		if (denominator != 0) {
			return numerator / (denominator);
		}
		return 0f;
	}

	/**
	 * 两个正整数相加
	 *
	 * @param n1
	 *            第一个参数
	 * @param n2
	 *            第二个参数
	 * @return 相加后的结果
	 * @exception IllegalArgumentException
	 *                ,如果n1或者n2有一个负数,则会抛出此异常;如果n1与n2相加后的结果是负数,即溢出了,也会抛出此异常
	 */
	public static int addPlusNumber(final int n1, final int n2) {
		if (n1 < 0 || n2 < 0) {
			throw new IllegalArgumentException(
					"Both n1 and n2 must be plus,but n1=" + n1 + " and n2 ="
							+ n2);
		}
		final int _sum = n1 + n2;
		if (_sum < 0) {
			throw new IllegalArgumentException(
					"Add n1 and n2 must be plus,but n1+n2=" + _sum);
		}
		return _sum;
	}

	/**
	 * 比较两个flaot是否相等，用{@link Float#equals()}实现
	 *
	 * @param floatA
	 * @param floatB
	 * @return
	 */
	public static boolean floatEquals(float floatA, float floatB) {
		return ((Float) floatA).equals(floatB);
	}

	/**
	 * 给定一系列事件的发生频率，以这个频率估计概率，随机选择一个事件发生
	 *
	 * @param frequencies
	 *            发生事件的频率数组
	 * @param excludeIndexSet
	 *            忽略的事件索引集合，这些事件的频率将被忽略，随机结果也不会返回这些索引<br/>
	 *            如果没有忽略的事件，可传入null
	 * @return 发生的事件索引，即在frequencies中的索引；频率全部为0则返回-1，表示没有事件发生
	 */
	public static int randomSelectByFrequency(final int[] frequencies,
			BitSet excludeIndexSet) {
		if (frequencies == null) {
			throw new IllegalArgumentException("frequencies is null");
		}
		if (frequencies.length == 0) {
			return -1;
		}
		int fromIndex = 0;
		int toIndex = frequencies.length;
		int total = 0;
		for (int i = fromIndex; i < toIndex; i++) {
			if (excludeIndexSet != null && excludeIndexSet.get(i)) {
				continue;
			}
			if (frequencies[i] < 0) {
				// 非法频率数据
				throw new IllegalArgumentException(
						"frequency must not be negative. freqencies:"
								+ Arrays.toString(frequencies));
			}
			total += frequencies[i];
		}
		if (total <= 0) {
			// 没有发生的事件
			return -1;
		}
		int randomNum = random(1, total), happenIndex = -1;
		int partSum = 0;
		for (int i = fromIndex; i < toIndex; i++) {
			if (excludeIndexSet != null && excludeIndexSet.get(i)) {
				continue;
			}
			partSum += frequencies[i];
			if (randomNum <= partSum) {
				happenIndex = i;
				break;
			}
		}
		return happenIndex;
	}

	/**
	 * 对一组数求平均，结果趋向于较大的数
	 * <p>算法：例如有一组数为{ a, b, c }，那么平均数 n = (a^4 + b^4 + c^4) / (a^3 + b^3 + c^3)
	 * <p><strong>要求给定的数全部大于0<br/>
	 * 计算过程使用int，三次方或者四次方后有可能会超出上限，所以不要传进太大的数</strong>
	 *
	 * @param nums 一组数
	 * @return 平均数
	 */
	public static int getAverageTendToGreater(int[] nums) {
		if (nums == null || nums.length == 0) {
			throw new IllegalArgumentException("no value");
		}
		if (nums.length == 1) {
			return nums[0];
		}
		int total4 = 0, total3 = 0;
		for (int i = 0; i < nums.length; i++) {
			int num = nums[i], tmp = 0;
			if (num <= 0) {
				throw new IllegalArgumentException("num error");
			}
			tmp = num * num * num;
			total3 += tmp;
			tmp *= num;
			total4 += tmp;
		}
		return (int)Math.round((double)total4 / total3);
	}

	/**
	 * 从一个数组中随机一个元素
	 *
	 * @param <T>
	 * @param array 一个数组
	 * @return 数组中的某个元素
	 */
	public static <T> T randomFromArray(T[] array) {
		int len = array.length;
		return array[random(0, len - 1)];
	}
	/**
	 * 随机一个数
	 * 
	 * @param num
	 * @return
	 */
	public static int randomNextInt(int num) {
		return random.nextInt(num);
	}
	
	/**
	 * 获取md5
	 * @param list
	 * @return
	 */
	public static byte[] getMd5(List<Integer> list){
		byte[] result = new byte[list.size()];
		for(int i = 0; i < list.size(); i++){
			int key = list.get(i);
			int keyByte = key & 0xFF;
			result[i] = (byte) keyByte;
		}
		return result;
	}

}
