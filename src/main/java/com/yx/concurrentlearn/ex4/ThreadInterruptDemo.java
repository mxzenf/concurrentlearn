package com.yx.concurrentlearn.ex4;

import org.apache.log4j.Logger;

/**
 * 使用线程
 * 
 * @author yangxin 2019年1月7日 上午10:15:56
 */
public class ThreadInterruptDemo {

  private static final Logger LOG = Logger.getLogger(ThreadInterruptDemo.class);

  public static void main(String[] args) throws InterruptedException {
    Thread thread1 = new Thread(() -> {

      try {
        while (!Thread.currentThread().isInterrupted()) {
          LOG.info("线程[" + Thread.currentThread().getName() + "]正在执行");
          Thread.sleep(1000L);
        }
      } catch (Exception e) {
        LOG.info("线程[" + Thread.currentThread().getName() + "]执行完毕");
        // e.printStackTrace();
      }

    });
    thread1.start();
    Thread.sleep(5000L);
    thread1.interrupt();
    // LOG.info(thread1.isInterrupted());
    // LOG.info(thread1.isInterrupted());
  }

}
