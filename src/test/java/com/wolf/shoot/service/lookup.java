package com.wolf.shoot.service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/2/13.
 */
public class lookup {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> sessions = new ConcurrentHashMap<String, String>();
        String t = new String("123");
        sessions.put(t, t);
        String ts = new String("123");
        System.out.println(sessions.get(ts));
    }
}
