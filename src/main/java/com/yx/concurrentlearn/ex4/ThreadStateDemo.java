package com.yx.concurrentlearn.ex4;

import org.apache.log4j.Logger;

/**
 * 
 * @author yangxin 2019年1月9日 上午11:12:49
 */
public class ThreadStateDemo {
  private static final Logger LOG = Logger.getLogger(ThreadStateDemo.class);

  public static void main(String[] args) throws InterruptedException {
    LOG.info("线程初始状态:" + Thread.State.NEW);
    LOG.info("线程运行状态:" + Thread.State.RUNNABLE);
    LOG.info("线程锁阻塞状态:" + Thread.State.BLOCKED);
    LOG.info("线程等待状态:" + Thread.State.WAITING);
    LOG.info("线程超时等待状态:" + Thread.State.TIMED_WAITING);
    LOG.info("线程完成状态:" + Thread.State.TERMINATED);
    
    Thread thread1 = new Thread(() -> {
      LOG.info("2,线程thread1运行start()后状态:" + Thread.currentThread().getState());
      try {
        Thread.sleep(100);
      } catch (Exception e) {
        e.printStackTrace();
      }
      LOG.info("4,线程thread1执行完后状态:" + Thread.currentThread().getState());
    });
    LOG.info("1,线程thread1创建后未调用start()方法状态:" + thread1.getState());
    thread1.start();
    Thread.sleep(50);
    LOG.info("3,线程thread1,sleep()调用后状态:" + thread1.getState());
    Thread.sleep(100);
    LOG.info("5,线程thread1执行完毕后状态:" + thread1.getState());
    
    Object lock = new Object();
    Thread thread2 = new Thread(()->{
      synchronized (lock) {
        try {
          Thread.sleep(50);
          lock.wait();
          LOG.info("4,线程thread2被notify()后状态" + Thread.currentThread().getState());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
    
    Thread thread3 = new Thread(()->{
      synchronized (lock) {
        
      }
    });
    thread2.start();
    Thread.sleep(30);
    thread3.start();
    LOG.info("1,线程thread2获取锁后状态" + thread2.getState());
    LOG.info("2,线程thread3等待锁状态:" + thread3.getState());
    Thread.sleep(50);
    LOG.info("3,线程thread2等待wait()方法状态" + thread2.getState());
    new Thread(()->{
      synchronized (lock) {
        lock.notify();
      }
    }).start();
    Thread.sleep(100);
    
  }
  
}
