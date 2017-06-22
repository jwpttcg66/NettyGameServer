package com.snowcattle.game.common.collections;

import java.util.Arrays;

/**
 * Created by jiangwenping on 2017/6/22.
 */
public class ArrayTest {
    public static void main(String[] args) {
        int a = Integer.valueOf("123");
        System.out.println(a);

        byte[] bytes = new byte[]{1,4,3};
        Arrays.sort(bytes);
        System.out.println(bytes);

        char[] chars = new char[]{'d','3', 'e'};
        String dest = Arrays.toString(chars);
        System.out.println(dest);
        char[] charDest = Arrays.copyOf(chars, 2);
        System.out.println(charDest);
    }
}
