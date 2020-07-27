package com.liuyao.demo.mashibing.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于CAS的锁
 */
public class T06_Lock extends Func{

    static Lock lock = new ReentrantLock();//cas

    public static void main(String[] args) {
//        test1(5);
//        testTryLock();
//        testInterruptibly();
//        testFair();
//        testCountDownLatch();
        testCyclicBarrier();

    }

    private static void test1(int times){

        Thread t = new Thread(()->{
            try {
                lock.lock();
                log("start");
                for (int i = 0; i < times; i++) {
                    msleep(1000);
                    log(i);
                }
            } finally {
                lock.unlock();
                log("end");
            }
        }, "t1");
        t.start();

    }

    //尝试加锁 失败则不进行操作
    private static void testTryLock(){
        test1(10);

        Thread t = new Thread(()->{
            boolean locked = false;
            try {
                locked = lock.tryLock(5, TimeUnit.SECONDS);
                log("start: " + locked);
                msleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                log("Interrupted");
            } finally {
                if (locked) lock.unlock();
                log("end");
            }
        }, "t2");

        t.start();
    }

    //可打断锁
    private static void testInterruptibly(){
        test1(20);
        Thread t = new Thread(()->{
            try {
                lock.lockInterruptibly();
                log("start");
                msleep(5000);
            } catch (InterruptedException e) {
                log("Interrupted");
                e.printStackTrace();
            } finally {
                lock.unlock();
                log("end");
            }
        }, "interrupted");
        t.start();
        msleep(5000);
        t.interrupt();
    }

    // 公平锁
    private static void testFair(){
        lock = new ReentrantLock(true); //公平锁 等待队列 先来先执行

        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(()->{
                try {
                    lock.lock();
                    log("get lock");
                } finally {
                    lock.unlock();
                }
            }, "t" + i);
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }

    }

    /**
     * 门栓
     * 等待线程结束 也可用join
     */
    private static void testCountDownLatch(){
        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(()->{
                int res = 0;
                for (int j = 0; j < 10000; j++) {
                    res += j;
                }
                latch.countDown();
                latch.countDown();//可以多次
            });
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        try {
            latch.await();//门栓在这等着 countDown等到最后一个线程结束 才继续执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log("end latch");
    }

    private static void testCyclicBarrier(){
        CyclicBarrier barrier = new CyclicBarrier(20, ()->{
           System.out.println("满人 发车.");
        });
//        barrier = new CyclicBarrier(20, new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("满人 发车.");
//            }
//        });

        for (int i = 0; i < 100; i++) {
            new Thread(()->{
                try {
                    barrier.await();//人不满 在这等着
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
