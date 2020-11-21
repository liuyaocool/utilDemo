package com.liuyao.demo.mashibing.thread;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * 马士兵高并发七
 *  Callable() Future(特征)
 */
public class T12_ThreadPool extends Func{

    public static void main(String[] args) {

        testFutureTask();
        testCollableFuture();
        testCompletableFuture();
    }


    /**
     * ThreadPoolExecutor
     *  corePollSize 核心线程
     *  maxiunpoolSize 最大线程
     *  keepAliveTime 多长时间没活干,归还OS,剩下核心线程
     *  unit
     *  workQueue
     *  threadFactory
     *  handler 拒绝策略
     *      Abort 不处理,抛异常
     *      Discard 不处理,不抛异常
     *      DiscardOldest 扔掉排队时间最久的,也就是最先加入的
     *      CallerRuns 调用者处理任务 --此处为方法调用者所在线程处理
     */
    public static void testThreadPoolExecutor(){
        class Task implements Runnable{
            private int i;
            public Task(int i) {
                this.i = i;
            }

            @Override
            public void run() {
                log("Task" + this.i);
                systemInRead();
            }

            @Override
            public String toString() {
                return "Task{" +
                        "i=" + i +
                        '}';
            }
        }
        ThreadPoolExecutor exe = new ThreadPoolExecutor(2, 4,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 8; i++) {
            exe.execute(new Task(i));
        }

        System.out.println(exe.getQueue());

        exe.execute(new Task(100));

        System.out.println(exe.getQueue());

        exe.shutdown();
    }

    /**
     * 管理多个Future的结果
     */
    private static void testCompletableFuture() {

        long start = System.currentTimeMillis();

        // 第一种
        CompletableFuture<Double> f1 = CompletableFuture.supplyAsync(() -> c1());
        CompletableFuture<Double> f2 = CompletableFuture.supplyAsync(() -> c2());
        CompletableFuture<Double> f3 = CompletableFuture.supplyAsync(() -> c3());

        CompletableFuture.allOf(f1, f2, f3).join();

        // 第二种
        CompletableFuture.supplyAsync(()->c1())
                .thenApply(String::valueOf)
                .thenApply(str -> "price " + str)
                .thenAccept(System.out::println);

        long end = System.currentTimeMillis();

        System.out.println("completable future " + (end - start));
    }

    public static Double c1 (){ msleep(new Random().nextInt(500)); return 1.0; }
    public static Double c2 (){ msleep(new Random().nextInt(500)); return 2.0; }
    public static Double c3 (){ msleep(new Random().nextInt(500)); return 3.0; }

    private static void testCollableFuture() {

        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> future = service.submit(() -> {
            return 1;
        });

        try {
            System.out.println(future.get()); // 阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        service.shutdown();
    }

    public static void testFutureTask(){

        FutureTask<Integer> task = new FutureTask<>(()->{
            return 1;
        });

        new Thread(task).start();

        try {
            System.out.println(task.get()); // 阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}


class MyExcutor implements Executor {

    @Override
    public void execute(Runnable command) {

    }
}
