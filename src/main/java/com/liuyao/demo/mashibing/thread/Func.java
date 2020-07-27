package com.liuyao.demo.mashibing.thread;

public class Func extends Thread{

    public static void msleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void log(Object str){
        System.out.println("Thread-" + Thread.currentThread().getName()
                + " --> " + String.valueOf(str));
    }


}
