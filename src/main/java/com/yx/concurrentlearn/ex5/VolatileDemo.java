package com.yx.concurrentlearn.ex5;

import org.apache.log4j.Logger;

/**
 * 可见性示例
 * @author yangxin 2019年1月10日 上午10:37:46
 */
public class VolatileDemo {

  private static volatile boolean gameover = false;
  private static volatile boolean end = false;
  private static final int DISTANCE = 1000;
  private static final Logger LOG = Logger.getLogger(VolatileDemo.class);

  public static void main(String[] args) {
    @SuppressWarnings("static-access")
    Thread referee = new Thread(() -> {
      while (!end) {
        Thread.currentThread().yield();
      }
      gameover = true;
      LOG.info("比赛结束");
    });
    Thread rabbit = new Thread(() -> {
      int speed = 10;
      int sum = 0;
      try {
        LOG.info("兔子开跑了");
        while(!gameover){
          Thread.sleep(10);
          sum += speed;
          if(sum >= DISTANCE){
            end = true;
            LOG.info("兔子赢得了冠军");
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    Thread tortoise = new Thread(() -> {
      int speed = 5;
      int sum = 0;
      try {
        LOG.info("乌龟开跑了");
        while(!gameover){
          Thread.sleep(20);
          sum += speed;
          if(sum >= DISTANCE){
            end = true;
            LOG.info("乌龟赢得了冠军");
          }
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
    LOG.info("开始比赛");
    referee.start();
    rabbit.start();
    tortoise.start();
    
  }

}
