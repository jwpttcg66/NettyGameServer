package com.snowcattle.game.common.collections;

import java.util.Arrays;

/**
 * Created by jiangwenping on 2017/6/22.
 */
public final class ArrayTest {
    public static void main(String[] args) {
        int a = Integer.valueOf("123");
        System.out.println(a);

        byte[] bytes = {1,4,3};
        Arrays.sort(bytes);
        System.out.println(Arrays.toString(bytes));

        char[] chars = {'d','3', 'e'};
        String dest = Arrays.toString(chars);
        System.out.println(dest);
        char[] charDest = Arrays.copyOf(chars, 2);
        System.out.println(charDest);
    }
}
