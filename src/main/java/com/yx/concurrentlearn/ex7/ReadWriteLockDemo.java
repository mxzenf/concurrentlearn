package com.yx.concurrentlearn.ex7;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 
 * @author yangxin 2019年2月14日 上午9:35:01
 */
public class ReadWriteLockDemo {
  
  private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

  public static void main(String[] args) throws InterruptedException {
    /*
     * 0 写写互斥
     * 1 写读互斥
     * 2 读读共享
     */
    int type = 2;
    switch (type) {
    case 0:
      Lock writeLock0 = lock.writeLock();
      new Thread(() -> {
        System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取写锁");
        writeLock0.lock();
        System.out.println("线程[" + Thread.currentThread().getName() + "]获得了写锁");
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          writeLock0.unlock();
          System.out.println("线程[" + Thread.currentThread().getName() + "]释放了写锁");
        }
      }).start();
      new Thread(() -> {
        System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取写锁");
        writeLock0.lock();
        System.out.println("线程[" + Thread.currentThread().getName() + "]获得了写锁");
        try {
          Thread.sleep(100);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          writeLock0.unlock();
          System.out.println("线程[" + Thread.currentThread().getName() + "]释放了写锁");
        }
      }).start();
      break;
    case 1:
      Lock writeLock1 = lock.writeLock();
      Lock readLock1 = lock.readLock();
      new Thread(() -> {
        System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取写锁");
        writeLock1.lock();
        System.out.println("线程[" + Thread.currentThread().getName() + "]获得了写锁");
        try {
          Thread.sleep(1100);
        } catch (Exception e2) {
          e2.printStackTrace();
        } finally {
          writeLock1.unlock();
          System.out.println("线程[" + Thread.currentThread().getName() + "]释放了写锁");
        }
      }).start();
      new Thread(() -> {
        System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取读锁");
        readLock1.lock();
        System.out.println("线程[" + Thread.currentThread().getName() + "]获得了读锁");
        try {
          Thread.sleep(1000);
        } catch (Exception e3) {
          e3.printStackTrace();
        } finally {
          readLock1.unlock();
          System.out.println("线程[" + Thread.currentThread().getName() + "]释放了读锁");
        }
      }).start();
      break;
    case 2:
      Lock readLock2 = lock.readLock();
      new Thread(() -> {
        System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取读锁");
        readLock2.lock();
        System.out.println("线程[" + Thread.currentThread().getName() + "]获得了读锁");
        try {
          Thread.sleep(1100);
        } catch (Exception e3) {
          e3.printStackTrace();
        } finally {
          readLock2.unlock();
          System.out.println("线程[" + Thread.currentThread().getName() + "]释放了读锁");
        }
      }).start();
      new Thread(() -> {
        System.out.println("线程[" + Thread.currentThread().getName() + "]尝试获取读锁");
        readLock2.lock();
        System.out.println("线程[" + Thread.currentThread().getName() + "]获得了读锁");
        try {
          Thread.sleep(1000);
        } catch (Exception e3) {
          e3.printStackTrace();
        } finally {
          readLock2.unlock();
          System.out.println("线程[" + Thread.currentThread().getName() + "]释放了读锁");
        }
      }).start();
      break;
    default:
      break;
    }
  }
  
}
