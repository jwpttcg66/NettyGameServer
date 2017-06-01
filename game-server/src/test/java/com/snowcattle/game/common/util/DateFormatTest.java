package com.snowcattle.game.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhenwei.liu created on 2013 13-8-29 下午5:35
 * @version $Id$
 *
 * 证明SimpleDateFormat为非线程安全
 */
public class DateFormatTest extends Thread {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    private String name;
    private String dateStr;
    private boolean sleep;

    public DateFormatTest(String name, String dateStr, boolean sleep) {
        this.name = name;
        this.dateStr = dateStr;
        this.sleep = sleep;
    }

    @Override
    public void run() {

        Date date = null;

        if (sleep) {
            try {
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(name + " : date: " + date);
    }

    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newCachedThreadPool();

        for(int i = 1991; i< 2200; i++){
            String dateString = String.valueOf(i) + "-09-13";
//            executor.execute(new DateFormatTest("A", "1991-09-13", true));
            executor.execute(new DateFormatTest("A", dateString, i%2==0));
        }


        executor.shutdown();
        try {
            Date date = sdf.parse("2033-09-13");
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}