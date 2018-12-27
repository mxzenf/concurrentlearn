package com.yx.concurrentlearn.ex2;

import org.apache.log4j.Logger;
/**
 * 
 * @author yangxin 2018年12月27日 上午10:49:56
 */
public class ThreadPriorityDemo {
  private final static Logger LOGGER = Logger.getLogger(ThreadPriorityDemo.class);

  static class MyThread extends Thread{
    
    public MyThread(String name){
      super(name);
    }
    
    @Override
    public void run() {
      Integer interval = 5000;
      try {
        LOGGER.info("线程[" + getName() + "]开始执行");
        Thread.sleep(interval);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally{
        LOGGER.info("线程[" + getName() + "]执行完成");
      };
    }
  }
  
  public static void main(String[] args) {
    int num = 21;
    Thread[] threads = new Thread[num];
    
    for (int i = 0;i < num; i++) {
      if (i < num/3) {
        threads[i] = new MyThread("高优先级线程" + i);
        threads[i].setPriority(Thread.MAX_PRIORITY);
      } else if (i >= num/3 && i < 2*(num/3)){
        threads[i] = new MyThread("普通优先级线程" + i);
        threads[i].setPriority(Thread.NORM_PRIORITY);
      } else {
        threads[i] = new MyThread("低优先级线程" + i);
        threads[i].setPriority(Thread.MIN_PRIORITY);
      }
    }
    
    for (int i = 0;i < num; i++) {
      threads[i].start();
    }
  }
  
}
