package com.yx.concurrentlearn.ex4;

import org.apache.log4j.Logger;

/**
 * 线程基础方法示例
 * @author yangxin 2019年1月4日 上午11:03:24
 */
public class ThreadBasicDemo {

  private static final Logger log = Logger.getLogger(ThreadBasicDemo.class);
  
  public static void main(String[] args) throws InterruptedException {
    log.info("没有执行自定义线程，当前线程数：" + Thread.activeCount());
    new Thread( () -> {
      try {
        Thread.sleep(10);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } ).start();
    log.info("执行一个匿名线程后，当前线程数：" + Thread.activeCount());
    Thread.sleep(1000);//等待匿名线程结束
    log.info("匿名线程结束后，当前线程数：" + Thread.activeCount());
    
    Thread currentThread = Thread.currentThread();
    log.info("当前线程名字：" + currentThread.getName());
    log.info("当前线程优先级：" + currentThread.getPriority());
    log.info("当前线程标识符：" + currentThread.getId());
    log.info("当前线程状态：" + currentThread.getState());
    
    Thread normalThead = new Thread( () -> {
      try {
        Thread.sleep(1000L);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } );
    Thread daemonThread = new Thread( () -> {
      try {
      } catch (Exception e) {
        e.printStackTrace();
      }
    } );
    daemonThread.setDaemon(true);
    normalThead.start();
    daemonThread.start();
    log.info("normalThread执行完成前是否存活：" + normalThead.isAlive());
    log.info("normalThread未执行完daemonThread是否存活：" + daemonThread.isAlive());
    Thread.sleep(2000L);//等待线程结束
    log.info("normalThread是否是守护线程：" + normalThead.isDaemon());
    log.info("normalThread是否存活：" + normalThead.isAlive());
    log.info("daemonThread是否是守护线程：" + daemonThread.isDaemon());
    log.info("daemonThread是否存活：" + daemonThread.isAlive());
  }
  
}
