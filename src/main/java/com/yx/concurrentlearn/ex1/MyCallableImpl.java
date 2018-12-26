package com.yx.concurrentlearn.ex1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.RandomUtils;
import org.apache.log4j.Logger;
/**
 * 
 * @author yangxin 2018年12月26日
 */
public class MyCallableImpl implements Callable<Integer> {
  
  private static final Logger LOGGER = Logger.getLogger(MyCallableImpl.class);

  @Override
  public Integer call() throws Exception {
    Integer interval = RandomUtils.nextInt(2000, 5000);
    LOGGER.info("开始执行线程，预计时间：" + interval);
    Thread.sleep(interval);
    return interval;
  }
  
  public static void main(String[] args) {
    ExecutorService eService = null;
    Future<Integer> future = null;
    try {
      eService =  Executors.newFixedThreadPool(5);
      future = eService.submit(new MyCallableImpl());
      Integer interval = future.get(1, TimeUnit.SECONDS);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      LOGGER.info("线程是否仍然在执行:" + future.isDone());
      LOGGER.info("线程是否取消:" + future.isCancelled());
      future.cancel(true);
      LOGGER.info("线程是否仍然在执行:" + future.isDone());
      LOGGER.info("线程是否取消:" + future.isCancelled());
    } finally{
      if(null != eService){
        eService.shutdown();
      }
    }
  }

}
