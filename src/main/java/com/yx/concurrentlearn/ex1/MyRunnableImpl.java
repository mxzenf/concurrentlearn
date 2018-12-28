package com.yx.concurrentlearn.ex1;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author yangxin 2018年12月26日
 */
public class MyRunnableImpl implements Runnable {
  
  private final static Logger LOGGER = Logger.getLogger(MyRunnableImpl.class);

  private String name;
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public MyRunnableImpl(){}
  
  public MyRunnableImpl(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    Integer interval = RandomUtils.nextInt(1000, 2000);
    try {
      LOGGER.info("线程" + getName() + "开始执行." + "预计执行时间" + interval);
      Thread.sleep(interval);
    } catch (Exception e) {
    } finally{
      LOGGER.info("线程" + getName() + "执行完毕");
    }
  }
  
  public static void main(String[] args) {
    for (int i = 0; i < 5; i++) {
      new Thread(new MyRunnableImpl(String.valueOf(i))).start();
    }
    for (int i = 5; i < 10; i++) {
      new Thread(() -> System.out.println("线程[" + Thread.currentThread().getName() + "]")).start();
    }
  }

}
