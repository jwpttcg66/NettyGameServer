package com.snowcattle.game.db.util;

import java.util.List;
import java.util.Random;

/**
 * Created by jwp on 2017/2/28.
 */
public class RandomUtil {

    private static Random random = new Random();

    /**
     * 获取一个范围内的随机值
     *
     * @param randomMin
     *            区间起始值
     * @param randomMax
     *            区间结束值
     * @return 返回左开右闭区间的一个随机值
     */
    public static int nextInt(int randomMin, int randomMax) {
        int randomBase = randomMax - randomMin;
        if (randomBase < 0) {
            throw new IllegalArgumentException(
                    "randomMax must be bigger than randomMin");
        } else if (randomBase == 0) {
            return randomMin;
        } else {
            return (random.nextInt(randomBase) + randomMin);
        }
    }

    /**
     * 获取一个范围内的随机值结果为闭区间
     *
     * @param randomMin
     *            区间起始值
     * @param randomMax
     *            区间结束值
     * @return 返回全闭区间的一个随机值
     */
    public static int nextEntireInt(int randomMin, int randomMax) {
        int randomBase = randomMax - randomMin +1;
        if (randomBase < 0) {
            throw new IllegalArgumentException(
                    "randomMax must be bigger than randomMin");
        } else if (randomBase == 0) {
            return randomMin;
        } else {
            return (random.nextInt(randomBase) + randomMin);
        }
    }


    /**
     * 按权重随机抽取一个
     * @param listRate
     * @param rateMaxNum
     */
    public static int getRadomIndex(List<Integer> listRate,int rateMaxNum) {
        int left = 0;
        int right = 0;
        int index = -1;
        int random = nextInt(0, rateMaxNum);
        for(int i=0; i < listRate.size(); i++){
            right = left + listRate.get(i);
            if(random >= left && random < right){
                index = i;
                break;
            }
            left = right;
        }
        return index;
    }

}

