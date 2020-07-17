package com.liuyao.demo.mashibing.thread;

public class Func extends Thread{

    public static void sleep(long time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void log(String str){
        System.out.println("Thread-" + Thread.currentThread().getName() + " --> " + str);
    }


}
