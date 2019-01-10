package com.yx.concurrentlearn.ex4;

import org.apache.log4j.Logger;

/**
 * 
 * @author yangxin 2019年1月8日 上午10:57:48
 */
public class ThreadJoinDemo {

  private static String config = "未配置";
  private static final Logger LOG = Logger.getLogger(ThreadJoinDemo.class);
  
  public static void main(String[] args) {
    new Thread( ()->{
      LOG.info("主线程开始执行");
      Thread otherThread = new Thread( ()->{
        LOG.info("子线程开始执行");
        try {
          config = "子线程进行配置";
          Thread.sleep(1000);
        } catch (Exception e) {
          e.printStackTrace();
        }
      } );
      try {
        LOG.info("主线程等待子线程执行完毕");
        otherThread.start();
        otherThread.join();
      } catch (Exception e) {
        e.printStackTrace();
      }
      LOG.info("主线程执行完毕");
      LOG.info("主线程获取程配置：" + config);
    } ).start();
  }
  
}
