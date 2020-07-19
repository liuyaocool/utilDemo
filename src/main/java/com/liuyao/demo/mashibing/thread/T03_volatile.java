package com.liuyao.demo.mashibing.thread;

import java.util.concurrent.TimeUnit;

/**
 * volatile
 * 保证线程可见性
 *      MESI CPU缓存一致性协议
 *      多个线程不运行在同一个cpu上
 * 禁止指令重排序
 *
 *
 * 堆内存 所有线程共享
 *      当前线程会copy一份堆内存的共享数据，两个线程会出现两份数据，
 *      当一个线程修改了这个值，另一个线程不会立马读到新值
 */
public class T03_volatile extends Func {

    /*volatile*/ boolean running = true;
    void m(){
        log("m start");
        while (running){
        }
        log(("m end"));
    }

    public static void main(String[] args) {

        testVisible();

    }

    //测试线程可见
    private static void testVisible(){
        T03_volatile t = new T03_volatile();

        new Thread(t::m, "t1").start();

        msleep(1000);

        t.running = false;
    }

}
