package com.yx.concurrentlearn.ex9;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author yangxin 2019年3月4日 上午10:02:44
 */
public class ExecutorsDemo {
  public static class MyThread implements Runnable {
    private String name;

    public MyThread(String name) {
      this.name = name;
    }

    @Override
    public void run() {
      System.out.println("线程：" + this.name + "执行中");
      try {
        Thread.sleep(1100);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      System.out.println("线程：" + this.name + "执行完成");
    }
  }

  public static void main(String[] args) throws InterruptedException {
    // 线程工厂创建线程
    ThreadFactory threadFactory = Executors.defaultThreadFactory();
    // 创建五个线程执行
    for (int i = 0; i < 5; i++) {
      threadFactory.newThread(() -> {
        System.out.println("线程:" + Thread.currentThread().getName());
      }).start();
    }
    // 创建单线程的线程池
    ExecutorService singleTreadPool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS,
        new LinkedBlockingDeque<Runnable>());
    Thread.sleep(1000);
    singleTreadPool.submit(new MyThread("线程1"));
    singleTreadPool.submit(new MyThread("线程2"));
    singleTreadPool.shutdown();

    // 创建cache线程池，等同于Executors.newCachedThreadPool
    ExecutorService cachedTreadPool = new ThreadPoolExecutor(2, Integer.MAX_VALUE, 1, TimeUnit.SECONDS,
        new ArrayBlockingQueue<Runnable>(10));
    Thread.sleep(1000);
    cachedTreadPool.submit(new MyThread("线程3"));
    cachedTreadPool.submit(new MyThread("线程4"));
    cachedTreadPool.submit(new MyThread("线程5"));
    cachedTreadPool.shutdown();

    // 创建fixed线程池，newFixedThreadPool
    ExecutorService fixedTreadPool = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS,
        new LinkedBlockingDeque<Runnable>());
    Thread.sleep(1000);
    fixedTreadPool.submit(new MyThread("线程6"));
    fixedTreadPool.submit(new MyThread("线程7"));
    fixedTreadPool.submit(new MyThread("线程8"));
    fixedTreadPool.shutdown();
  }

}
