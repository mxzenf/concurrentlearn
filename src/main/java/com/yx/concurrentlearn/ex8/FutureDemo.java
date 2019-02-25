package com.yx.concurrentlearn.ex8;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.RandomUtils;

/**
 * 
 * @author yangxin 2019年2月22日 上午9:42:07
 */
public class FutureDemo {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService executorService = Executors.newCachedThreadPool();
    Callable<Integer> callable = new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        Thread.sleep(3000);
        return RandomUtils.nextInt(100,200);
      }
    };
    
    Future<Integer> future = executorService.submit(callable);
    Thread.sleep(1000);
    System.out.println("1000毫秒后任务是否完成:" + future.isDone());
    System.out.println("1000毫秒后任务是否完成:" + future.isCancelled());
    future.cancel(true);
    Thread.sleep(3000);
    System.out.println("cancle后任务是否完成:" + future.isDone());
    System.out.println("cancle后任务是否完成:" + future.isCancelled());
    
    future = executorService.submit(callable);
    try {
      future.get(2, TimeUnit.SECONDS);
    } catch (ExecutionException | TimeoutException e) {
      System.out.println("等待2秒超时未获取结果");
      System.out.println("超时等待任务是否完成:" + future.isDone());
      System.out.println("超时等待任务是否完成:" + future.isCancelled());
    }
    Thread.sleep(2000);
    System.out.println("继续等待2秒后获取等待结果:" + future.get());
    
    executorService.shutdown();
  }
  
}
