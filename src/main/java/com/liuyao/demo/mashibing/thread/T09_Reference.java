package com.liuyao.demo.mashibing.thread;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

public class T09_Reference extends Func{


    public static void main(String[] args) {
        normal();
    }

    /**
     * 强引用
     */
    public static void normal(){
        M m = new M();
        System.gc();
        msleep(1000);
        System.out.println("finish sleep 1s");

        m = null;
        System.gc();
        msleep(1000);
        System.out.println("finish sleep 1s");
    }

    /**
     * 软引用
     * 需要配置启动参数进行测试
     *  -Xms20M -Xmx20M 分配20M堆内存(heap)
     */
    public static void soft(){
        // 10M
        SoftReference<byte[]> soft = new SoftReference<>(new byte[1024*1024*10]);
        System.out.println(soft.get());
        System.gc();

        msleep(1000);

        System.out.println(soft.get());

        // 再分配数组,堆内存将装不下,这时候系统会垃圾回收,先回收一次,如果不够,会把软引用干掉
        byte[] b = new byte[1024*1024*20];
        System.out.println(soft.get());
    }

    /**
     * 弱引用
     *  只要垃圾回收 直接干掉
     *  一般用在容器里
     *  如果有一个强引用指向,只要强引用消失,就会被回收
     *
     *  WeakHashMap
     */
    public static void weak(){
        WeakReference<M> m = new WeakReference<>(new M());
        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());

        // 62 01:41:37

    }


}

class M{
    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize");
    }
}
