package com.liuyao.demo.mashibing.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS 无锁优化 自旋
 *  Atomic***
 *  cpu原语支持 不能被打断
 *  compare and set --Unsafe cpu原语
 *
 *  cas(v, expected, newval)
 *      if v == e //此时不能有另一个线程修改值，因为cpu原语支持，不能被打断
 *          v = newval
 *      else
 *          try again or fail
 *
 * ABA问题：
 *  基础数据类型没问题，
 *  引用类型有问题，线程先改变指向B，修改了B的一些内容，又指回A(内部指向B)，指向不变，但指向的内容已经变了 --前女友问题
 *  某个线程把值先变成2，又变成了1
 *  加版本号即可解决
 */
public class T04_CAS {

    AtomicInteger count = new AtomicInteger(0);

    /*synchronized*/ void m1(){
        for (int i = 0; i < 10000; i++) {
            count.incrementAndGet();// count++
        }
    }

    private static void testAtomicInteger(){
        T04_CAS t = new T04_CAS();

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Thread(t::m1, "atom-" + i));
        }
        list.forEach((o) -> o.start());

        list.forEach((o) -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count);
    }

    public static void main(String[] args) {
        testAtomicInteger();
    }
}
