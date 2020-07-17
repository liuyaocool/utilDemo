package com.liuyao.demo.mashibing.thread;

public class T01_Basic extends Func{

    public static void main(String[] args) {

        System.out.println("main start.");

        testJoin();
//        testYield();


        for (int i = 0; i < 10; i++) {
            System.out.println("main-" + i);
            sleep(500);
        }

        System.out.println("main end.");


    }

    private static void testJoin(){

        //lambda after jdk8
        Thread join = new Thread(()->{
            System.out.println("join start.");
            for (int i = 0; i < 10; i++) {
                System.out.println("join-"+i);
                sleep(500);
            }
            System.out.println("join end.");
        });
        join.start();

        new Thread(()->{
            System.out.println("t1 start.");
            for (int i = 0; i < 10; i++) {
                System.out.println("t1-"+i);
                sleep(500);
                if (i == 5){
                    try {
                        join.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("t1 end.");
        }).start();
    }


    private static void testYield(){


        // yield 重新扔到等待队列 返回就绪状态 让出一下cpu 下次抢到在当前位置继续执行
        new Thread(()->{
            System.out.println("yield start.");
            for (int i = 0; i < 100; i++) {
                sleep(50);
                if (i % 10 == 0){
                    System.out.println("yield-" + i);
                    Thread.yield();
                }
            }
            System.out.println("yield end.");
        }).start();

        new Thread(()->{
            System.out.println("yield2 start.");
            for (int i = 0; i < 100; i++) {
                sleep(50);
                if (i % 10 == 0){
                    System.out.println("yield2-" + i);
                    Thread.yield();
                }
            }
            System.out.println("yield2 end.");
        }).start();

    }

}
