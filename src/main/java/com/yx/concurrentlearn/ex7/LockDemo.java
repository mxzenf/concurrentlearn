package com.yx.concurrentlearn.ex7;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author yangxin 2019年1月23日 上午11:21:44
 */
public class LockDemo {

  private final static Lock lock = new ReentrantLock(false);

  public static void main(String[] args) throws InterruptedException {
    new Thread(() -> {
      try {
        lock.lock();
        System.out.println("线程[" + Thread.currentThread().getName() + "]获得了锁");
        Thread.sleep(5000);
      } catch (InterruptedException ie) {
        ie.printStackTrace();
      } finally {
        lock.unlock();
        System.out.println("线程[" + Thread.currentThread().getName() + "]释放了锁");
      }
    }).start();

    new Thread(() -> {
      lock.lock();
      try {
        System.out.println("线程[" + Thread.currentThread().getName() + "]获得了锁");
        Thread.sleep(10);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
        System.out.println("线程[" + Thread.currentThread().getName() + "]释放了锁");
      }
    }).start();

    new Thread(() -> {
      if (lock.tryLock()) {
        try {
          System.out.println("线程[" + Thread.currentThread().getName() + "]获得了锁");
          Thread.sleep(10);
        } catch (Exception e) {
          e.printStackTrace();
        } finally {
          lock.unlock();
          System.out.println("线程[" + Thread.currentThread().getName() + "]释放了锁");
        }
      } else {
        System.out.println("线程[" + Thread.currentThread().getName() + "]线程未获取锁");
      }
    }).start();

    new Thread(() -> {
      try {
        if (lock.tryLock(1, TimeUnit.SECONDS)) {
          try {
            System.out.println("线程[" + Thread.currentThread().getName() + "]获得了锁");
            Thread.sleep(10);
          } finally {
            lock.unlock();
            System.out.println("线程[" + Thread.currentThread().getName() + "]释放了锁");
          }
          
        } else {
          System.out.println("线程[" + Thread.currentThread().getName() + "]等待1秒后未未获取锁");
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }).start();
    
    Thread thread4 = new Thread(()->{
      try {
        lock.lockInterruptibly();
        System.out.println("线程[" + Thread.currentThread().getName() + "]获得了锁");
        try {
          Thread.sleep(10);
        } finally {
          lock.unlock();
          System.out.println("线程[" + Thread.currentThread().getName() + "]释放了锁");
        }
        
      } catch (InterruptedException e) {
        System.out.println("线程[" + Thread.currentThread().getName() + "]被中断未获取锁");
      }
    });
    thread4.start();
    Thread.sleep(1000);
    thread4.interrupt();
  }

}
