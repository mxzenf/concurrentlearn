package com.yx.concurrentlearn.ex6;

/**
 * 
 * @author yangxin 2019年1月16日 上午9:36:59
 */
public class SynchronizedDemo {

  private static int number = 0;
  private static Object lock = new Object();
  private static byte[] lock1 = new byte[1];

  static class Increment {
    public synchronized void increment() {
      number++;
    }
  }
  
  static class SynClassBlockIncrement extends Increment{
    public void increment(){
      synchronized (SynClassBlockIncrement.class) {
        super.increment();
      }
    }
  }
  
  static class SynStaticBlockIncrement {
    public static void increment() {
      number++;
    } 
  }
  
  static class SynStaticBlockIncrement1 {
    public synchronized static void increment() {
      number++;
    } 
  }
  
  static class SynObjectBlockIncrement {

    public void increment(){
      synchronized (lock) {
        number++;
      }
    } 
  }
  
  static class SynObjectBlockIncrement1 {
    public void increment(){
      synchronized (lock1) {
        number++;
      }
    } 
  }
  
  @SuppressWarnings("all")
  public static void main(String[] args) throws InterruptedException {

    int type = 32;
    int count = 10000;
    
    switch (type) {
    case 10://synchronized应用到方法
      for (int i = 0; i < count; i++) {
        new Thread(()->{
          new Increment().increment();
        }).start();
      }
      Thread.sleep(2000);
      System.out.println("不同对象同步代码块控制增加过后：" + number);
      break;
    case 11://同一个对象同步方法
      Increment increment = new Increment();
      for (int i = 0; i < count; i++) {
        new Thread(()->{
          increment.increment();
        }).start();
      }
      Thread.sleep(2000);
      System.out.println("同一个对象同步方法增加后结果：" + number);
      break;
    case 20://多个对象使用使用class作为锁
      for (int i = 0; i < count; i++) {
        new Thread(()->{
          new SynClassBlockIncrement().increment();
        }).start();
      }
      Thread.sleep(2000);
      System.out.println("使用class作为锁自增后：" + number);
      break;
    case 30://多个对象静态方法无锁
      for (int i = 0; i < count; i++) {
        new Thread(()->{
          new SynStaticBlockIncrement().increment();
        }).start();
      }
      Thread.sleep(2000);;
      System.out.println("静态方法无锁自增后：" + number);
      break;
    case 31://多个对象静态方法有锁
      for (int i = 0; i < count; i++) {
        new Thread(()->{
          new SynStaticBlockIncrement1().increment();
        }).start();
      }
      Thread.sleep(2000);
      System.out.println("静态方法有锁自增后：" + number);
      break;
    case 32://基本类型作为锁
      for (int i = 0; i < count; i++) {
        new Thread(()->{
          new SynObjectBlockIncrement1().increment();
        }).start();
      }
      Thread.sleep(2000);
      System.out.println("基本类型为锁自增后：" + number);
      break;
    default:
      break;
    }

  }

}
