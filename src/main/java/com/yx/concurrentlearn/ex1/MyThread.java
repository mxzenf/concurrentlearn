package com.yx.concurrentlearn.ex1;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author yangxin 2018年12月26日
 */
public class MyThread extends Thread {
  private static final Logger LOGGER = Logger.getLogger(MyThread.class);

  public MyThread(String name) {
    super(name);
  }

  @Override
  public void run() {
    int interval = RandomUtils.nextInt(100, 500);
    try {
      LOGGER.info("线程" + getName() + "开始执行." + "预计执行时间" + interval);
      Thread.sleep(interval);
    } catch (Exception e) {
    } finally {
      LOGGER.info("线程[" + getName() + "]运行完毕");
    }
  }

  public static void main(String[] args) {
    for (int i = 0;i < 5; i++) {
      new MyThread(String.valueOf(i)).start();
    }
  }

}
