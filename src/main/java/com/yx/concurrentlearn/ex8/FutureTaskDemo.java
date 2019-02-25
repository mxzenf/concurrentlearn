package com.yx.concurrentlearn.ex8;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.commons.lang3.RandomUtils;

/**
 * 
 * @author yangxin 2019年2月25日 上午10:32:25
 */
public class FutureTaskDemo {

  public static void main(String[] args) throws InterruptedException, ExecutionException {
    ExecutorService service = Executors.newCachedThreadPool();
    Callable<Integer> callable = new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        return RandomUtils.nextInt(100, 200);
      }
    };
    
    FutureTask<Integer> ft = new FutureTask<Integer>(callable);
    service.submit(ft);
    System.out.println("使用FutureTask执行任务获取结果：" + ft.get());
    
    service.shutdown();
  }
  
}
