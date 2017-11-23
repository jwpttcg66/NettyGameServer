package com.snowcattle.game.common.util.concurrent;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 2017/11/23.
 */
public class ConcurrentMapTest {
    public static void main(String[] args) {
        ConcurrentHashMap<Integer, Integer> hashMap = new ConcurrentHashMap<>();
        for(int i = 0; i< 1000; i++){
            Integer integer = hashMap.putIfAbsent(i, i);
            System.out.println(integer);
            Integer value = hashMap.putIfAbsent(i, i);
            System.out.println(value);
        }



    }
}
