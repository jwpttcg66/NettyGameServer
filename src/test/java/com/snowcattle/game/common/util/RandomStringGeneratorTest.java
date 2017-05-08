package com.snowcattle.game.common.util;

/**
 * Created by jiangwenping on 17/2/6.
 */
public class RandomStringGeneratorTest {
    public static void main(String[] args) {
       String string =  new RandomStringGenerator().generateRandomString(10);
        System.out.println(string);
    }
}
