package com.yx.concurrentlearn.ex8;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.RandomUtils;

/**
 * 
 * @author yangxin 2019年2月22日 上午9:14:04
 */
public class CallableBasicDemo {

  private static AtomicInteger value = new AtomicInteger(0);
  
  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    executorService.submit(new Runnable() {
      
      @Override
      public void run() {
        try {
          Thread.sleep(100);
          value.getAndSet(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }        
      }
    });
    while (0 == value.get());
    System.out.println("实现Runnable的线程获取value结果:" + value.get());
    
    Future<Integer> res = executorService.submit(new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        Thread.sleep(100);
        return RandomUtils.nextInt(100,200);
      }
    });
    System.out.println("实现Callable的线程获取value结果:" + res.get());
    executorService.shutdown();
  }
  
}
