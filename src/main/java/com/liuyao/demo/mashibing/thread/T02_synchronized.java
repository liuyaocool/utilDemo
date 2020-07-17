package com.liuyao.demo.mashibing.thread;

/**
 * synchronized 底层实现
 * JDK早期 重量级 - OS
 * 后来改进：锁升级 - 《我就是厕所所长》（1 2）（马士兵写）
 *      只有一个线程 不加锁
 *      线程争用 升级为自旋锁（适用：线程数少 线程执行时间短）
 *      10次后 升级为重量级锁-OS
 *
 * 注：不能加锁 String常量（跟JVM处理String的方式相关） Integer Long 等基本数据类型
 *
 */
public class T02_synchronized extends Func{

    public synchronized void m1(){
        log("m1 start");
        sleep(10000);
        log("m1 end");

    }

    public void m2(){
        log("m2 start");
        sleep(5000);
        log("m2 end");

    }

    synchronized void m3(){
        log("m3 start");
        sleep(1000);
        m4();
        log("m3 end");
    }

    synchronized void m4(){
        log("m4 start");
        sleep(1000);
        log("m4 end");
    }

    public static void main(String[] args) {
//        test1();
        test2();

    }

    public static void test1(){

        T02_synchronized t = new T02_synchronized();

        new Thread(t::m1, "t1").start();
        new Thread(t::m2, "t2").start();
    }

    public static void test2(){
        //可重入 同一锁可互相调用

        T02_synchronized t = new T02_synchronized();

        new Thread(t::m3, "t3").start();
    }

    //线程出现异常 默认锁会被释放
}
