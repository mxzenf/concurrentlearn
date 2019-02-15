package com.yx.concurrentlearn.ex7;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * @author yangxin 2019年2月11日 上午9:39:25
 */
public class ConditionDemo {

  private final static Lock lock = new ReentrantLock(false);

  public static void main(String[] args) throws InterruptedException {
    Condition condition = lock.newCondition();
    //等待被唤醒或者被中断
    Thread thread0 = new Thread(() -> {
      System.out.println("线程[await()-" + Thread.currentThread().getName() + "]尝试获取锁");
      lock.lock();
      System.out.println("线程[await()-" + Thread.currentThread().getName() + "]获取了锁");
      try {
        System.out.println("线程[await()-" + Thread.currentThread().getName() + "]等待");
        condition.await();
        System.out.println("线程[await()-" + Thread.currentThread().getName() + "]等待结束");
      } catch (InterruptedException e) {
        System.out.println("线程[await()-" + Thread.currentThread().getName() + "]被中断");
//        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    });
    //指定等待被唤醒时间
    Thread thread1 = new Thread(() -> {
      System.out.println("线程[await(long,TimeUnit)-" + Thread.currentThread().getName() + "]尝试获取锁");
      lock.lock();
      System.out.println("线程[await(long,TimeUnit)-" + Thread.currentThread().getName() + "]获取了锁");
      try {
        System.out.println("线程[await(long,TimeUnit)-" + Thread.currentThread().getName() + "]等待");
        boolean res = condition.await(2,TimeUnit.SECONDS);
        if (res) {
          System.out.println("线程[await(long,TimeUnit)-" + Thread.currentThread().getName() + "]被唤醒");
        } else {
          System.out.println("线程[await(long,TimeUnit)-" + Thread.currentThread().getName() + "]超过了等待时间");
        }
      } catch (InterruptedException e) {
        System.out.println("线程[await(long,TimeUnit)-" + Thread.currentThread().getName() + "]被中断");
//        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    });
    //不响应中断
    Thread thread2 = new Thread(() -> {
      System.out.println("线程[awaitUninterruptibly-" + Thread.currentThread().getName() + "]尝试获取锁");
      lock.lock();
      System.out.println("线程[awaitUninterruptibly-" + Thread.currentThread().getName() + "]获取了锁");
      try {
        System.out.println("线程[awaitUninterruptibly-" + Thread.currentThread().getName() + "]等待");
        condition.awaitUninterruptibly();
        System.out.println("线程[awaitUninterruptibly-" + Thread.currentThread().getName() + "]等待结束");
      } finally {
        lock.unlock();
      }
    });
    //等待5秒被唤醒
    Thread thread3 = new Thread(() -> {
      System.out.println("线程[awaitUntil-" + Thread.currentThread().getName() + "]尝试获取锁");
      lock.lock();
      System.out.println("线程[awaitUntil-" + Thread.currentThread().getName() + "]获取了锁");
      try {
        System.out.println("线程[awaitUntil-" + Thread.currentThread().getName() + "]等待");
        boolean res = condition.awaitUntil(new Date(System.currentTimeMillis() + 5000));
        if (res) {
          System.out.println("线程[awaitUntil-" + Thread.currentThread().getName() + "]被唤醒");
        } else {
          System.out.println("线程[awaitUntil-" + Thread.currentThread().getName() + "]超过了等待时间");
        }
      } catch (InterruptedException e) {
        System.out.println("线程[awaitUntil-" + Thread.currentThread().getName() + "]被中断");
//        e.printStackTrace();
      } finally {
        lock.unlock();
      }
    });
    
    thread0.start();
    thread1.start();
    thread2.start();
    thread3.start();
    /*
     * 0 等待自行结束
     * 1 中断
     * 2 唤醒所有
     */
    int type = 2;
    Thread.sleep(100);
    switch (type) {
    case 0:
      break;
    case 1:
      thread0.interrupt();
      thread1.interrupt();
      thread2.interrupt();
      thread3.interrupt();
      break;
    case 2:
      try {
        lock.lock();
        condition.signalAll();
      } finally {
        lock.unlock();
      }
      
      break;
    default:
      break;
    }
  }

}
