package com.snowcattle.game.common.reflect;

import org.springframework.cglib.reflect.FastClass;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by jiangwenping on 17/4/13.
 * 测试结果， fastclass是原生性能耗时的20倍
 */
public class ReflectTest {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class classes = ReflectTest.class;

        int size = 100000;
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < size; i++){
            classes.newInstance();
//            FastClass serviceFastClass = FastClass.create(classes);
//            serviceFastClass.newInstance();
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        long startTime2 = System.currentTimeMillis();
        for (int i = 0; i < size; i++){
//            classes.newInstance();
            FastClass serviceFastClass = FastClass.create(classes);
            serviceFastClass.newInstance();
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println(endTime2 - startTime2);
    }
}
