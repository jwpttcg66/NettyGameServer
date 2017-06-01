package com.snowcattle.game.common.lock;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 简单读写锁demo
 * @author hejingyuan
 *
 */
public class ReadWriteLockTest {
    public static void main(String[] args) {
        final Queue3 q3 = new Queue3();
        int threadSize = 10;
        //创建几个线程
        for(int i=0;i<threadSize;i++)
        {
            new Thread(){
                public void run(){
                    while(true){
                        q3.get();
                    }
                }

            }.start();

            new Thread(){
                public void run(){
                    while(true){
                        q3.put(new Random().nextInt(10000));
                        try {
                            Thread.sleep((long) (Math.random() * 1000));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }.start();

        }

    }
}

class Queue3{
    private Object data = null;//共享数据，只能有一个线程能写该数据，但可以有多个线程同时读该数据
    //读写锁
    ReadWriteLock rwl = new ReentrantReadWriteLock();
    //读数据
    public void get(){
        rwl.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " be ready to read data!");
            Thread.sleep((long)(Math.random()*1000));
            System.out.println(Thread.currentThread().getName() + "have read data :" + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            rwl.readLock().unlock();
        }
    }
    //写数据
    public void put(Object data){

        rwl.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " be ready to write data!");
            Thread.sleep((long)(Math.random()*1000));
            this.data = data;
            System.out.println(Thread.currentThread().getName() + " have write data: " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally{
            rwl.writeLock().unlock();
        }


    }
}
