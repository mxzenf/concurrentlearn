package com.yx.concurrentlearn.ex3;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;

/**
 * 
 * @author yangxin 2019年1月2日 下午4:14:36
 */
public class ConcurrentAtomicityDemo {

  private final static Logger LOGGER = Logger.getLogger(ConcurrentAtomicityDemo.class);

  static class Increment {

    public int count = 0;

    public void increment() {
      count++;
    }

    public int getCount() {
      return count;
    }

    public void setCount(int count) {
      this.count = count;
    }

  }

  static class SynchronizeIncrement extends Increment {
    @Override
    public synchronized void increment() {
      super.increment();
    }
  }

  static class AtomicIncrement {
    public AtomicInteger count = new AtomicInteger(0);

    public void increment() {
      count.getAndIncrement();
    }

    public AtomicInteger getCount() {
      return count;
    }
  }
  
  static class LockIncrement extends Increment {
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    @Override
    public void increment() {
      try {
        lock.writeLock().lock();
        super.increment();
      } finally {
        lock.writeLock().unlock();
      }
      
    }
  }

  public static void main(String[] args) throws InterruptedException {
    int num = 100000;
    int type = -1;
    /*
     * 0 无同步无锁 
     * 1 使用同步关键字 synchronized 
     * 2 使用读写锁
     * 3 使用原子类
     */
    Increment increment;
    switch (type) {
    case 0:
      // 对count进行++操作，先进行取值，再加，最后赋值，实际上是三个操作，多线程每次取值可能是旧的值就会产生
      // 实际结果和预期结果不一致
      increment = new Increment();
      for (int i = 0; i < num; i++) {
        new Thread(() -> {
          increment.increment();
        }).start();
      }
      Thread.sleep(5000L);
      LOGGER.info("应该增加[" + num + "]," + "实际增加[" + increment.getCount() + "]");
      break;
    case 1:
      increment = new SynchronizeIncrement();
      for (int i = 0; i < num; i++) {
        new Thread(() -> increment.increment()).start();
      }
      Thread.sleep(5000L);// 等待线程都执行完毕
      LOGGER.info("应该增加[" + num + "]," + "实际增加[" + increment.getCount() + "]");
      break;
    case 2:
      increment = new LockIncrement();
      for (int i = 0; i < num; i++) {
        new Thread(() -> increment.increment()).start();
      }
      Thread.sleep(5000L);// 等待线程都执行完毕
      LOGGER.info("应该增加[" + num + "]," + "实际增加[" + increment.getCount() + "]");
      break;
    case 3:
      AtomicIncrement increment1 = new AtomicIncrement();
      for (int i = 0; i < num; i++) {
        new Thread(() -> increment1.increment()).start();
      }
      Thread.sleep(5000L);
      LOGGER.info("应该增加[" + num + "]," + "实际增加[" + increment1.getCount() + "]");
    default:
      break;
    }
    
    LOGGER.info("main方法执行完毕行");
  }
}
