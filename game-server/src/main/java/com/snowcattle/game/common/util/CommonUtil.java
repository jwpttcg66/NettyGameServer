package com.snowcattle.game.common.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class CommonUtil {
    /**
     * 将指定byte数组以16进制的形式打印到控制台
     * @param hint String
     * @param b byte[]
     * @return void
     */
   public static void printHexString(String hint, byte[] b) {
      System.out.print(hint);
      for (int i = 0; i < b.length; i++) {
        String hex = Integer.toHexString(b[i] & 0xFF);
        if (hex.length() == 1) {
          hex = '0' + hex;
        }
        System.out.print(hex.toUpperCase() + " ");
      }
      System.out.println("");
   }
   /**
     *
     * @param b byte[]
     * @return String
     */
   public static String Bytes2HexString(byte[] b) {
      String ret = "";
      for (int i = 0; i < b.length; i++) {
        String hex = Integer.toHexString(b[i] & 0xFF);
        if (hex.length() == 1) {
          hex = '0' + hex;
        }
        ret += hex.toUpperCase();
      }
      return ret;
   }
   /**
     * 将两个ASCII字符合成一个字节；
     * 如："EF"--> 0xEF
     * @param src0 byte
     * @param src1 byte
     * @return byte
     */
   public static byte uniteBytes(byte src0, byte src1) {
      byte _b0 = Byte.decode("0x" + new String(new byte[]{src0})).byteValue();
      _b0 = (byte)(_b0 << 4);
      byte _b1 = Byte.decode("0x" + new String(new byte[]{src1})).byteValue();
      byte ret = (byte)(_b0 ^ _b1);
      return ret;
   }
   /**
     * 将指定字符串src，以每两个字符分割转换为16进制形式
     * 如："2B44EFD9" --> byte[]{0x2B, 0x44, 0xEF, 0xD9}
     * @param src String
     * @return byte[]
     */
   public static byte[] HexString2Bytes(String src){
      byte[] ret = new byte[8];
      byte[] tmp = src.getBytes();
      for(int i=0; i<8; i++){
        ret[i] = uniteBytes(tmp[i*2], tmp[i*2+1]);
      }
      return ret;
   }

	/**
	 * 计算rate/100000几率
	 * @param rate
	 * @return true 成功，false失败
	 */
	public static boolean getRate(int rate){
//		int percentageOfPrecision = GlobalManager.getGameConstants().getPercentageOfPrecision();

		if(rate <= 0){
			return false;
		}
		if(rate >= 100000){
			return true;
		}
		int[] rateArray = new int[2];
		rateArray[0] = rate;
		rateArray[1] = 100000 - rate;
		int index = MathUtils.random(rateArray);
		return (index == 0);
	}

	public static String exceptionToString(Exception e){
		StackTraceElement[] ste = e.getStackTrace();
		StringBuilder sb = new StringBuilder();
		String name = e.getClass().getName();
        String message = e.getMessage();
        String content = (message != null) ? (name + ": " + message) : name;
		sb.append(content + "\n");
		for(StackTraceElement s : ste){
			sb.append(s.toString() + "\n");
		}
		return sb.toString();
	}
	
	/**
	 * 根据自己的位置数[selfPos]，按照间隔[interval]，取指定数量[resultMax]个位置数
	 * 注：从自己开始，即含自身
	 * 
	 * @param selfPos 自己的位置数或起始的位置数，最小为1
	 * @param interval 位置数的间隔
	 * @param resultMax 一共取多少个位置数
	 * @return
	 */
	public static List<Integer> getTargetList(int selfPos, int interval, int resultMax) {
		List<Integer> targetPosList = new LinkedList<Integer>();
		if (selfPos <= 0 || interval <= 0 || resultMax <= 0) {
			return targetPosList;
		}
		
		// 当自己所在位置小于结果最大数时，直接取结果最大数对应的列表，即1-resultMax
		if (selfPos <= resultMax) {
			interval = 1;
			selfPos = resultMax;
		} else {
			// 从自己位置开始，往前面取resultMax个数，含自己
		}
		
		for (int count = 0; count < resultMax; count++) {
			int target = selfPos - interval * count;
			if (target <= 0) {
				break;
			}
			targetPosList.add(target);
		}
		return targetPosList;
	}
	
	/**
	 * 取列表中某一对象附近的几个对象
	 * 注：从左右两边各取nearByNum个+自身。如果其中一头不足，则从另一头补足，不考虑两头都不足的情况！
	 * 如果自己没在列表中，则默认为列表的最后一个
	 * 
	 * @param <T> 需要实现equals接口，因为要先找到自己的位置
	 * @param listAll 总列表
	 * @param self 自己，如果自己没在列表中，则默认为列表的最后一个
	 * @param nearByNum 每侧取多少个 
	 * @return 返回列表总数为 nearByNum*2+1
	 */
	public static <T> List<T> getNearByList(List<T> listAll, T self, int nearByNum) {
		List<T> nearByList = new ArrayList<T>();
		
		int totalIndex = listAll.size() - 1;
		int selfIndex = listAll.indexOf(self);
		if (selfIndex == -1) {
			// 没找到自身时，则设置自身为最后一个
			selfIndex = totalIndex;
		}
		int startIndex = selfIndex - nearByNum;
		int endIndex = selfIndex + nearByNum;
		
		int addition = 0;
		// 左边不足时，从右边补齐
		if (startIndex < 0) {
			addition = 0 - startIndex;
			startIndex = 0;
			endIndex += addition;
		} else if (endIndex > totalIndex) {
			// 右边不足时，从左边补齐
			addition = endIndex - totalIndex;
			endIndex = totalIndex;
			startIndex -= addition;
		}
		
		if (startIndex < 0) {
			startIndex = 0;
		}
		if (endIndex > totalIndex) {
			endIndex = totalIndex;
		}
		
		nearByList = listAll.subList(startIndex, endIndex + 1);
		
		return nearByList;
	}
	
	public static byte boolean2Byte(boolean value)
	{
		return (byte) (value ? 1 : 0);
	}
	
	public static boolean byte2Boolean(byte value)
	{
		if(value == 1)
			return true;
		else if(value == 0)
			return false;
		else
			throw new IllegalArgumentException("boolean transfer errorֵ:" + value);
	}
}
