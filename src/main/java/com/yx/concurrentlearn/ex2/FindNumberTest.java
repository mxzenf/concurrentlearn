package com.yx.concurrentlearn.ex2;

import java.util.Arrays;

/**
 * 利用lamdba寻找符合条件的数字
 * @author yangxin 2018年12月29日 下午4:21:25
 */
public class FindNumberTest {
  
  public static Integer[] findNumbers(Integer[] arr,FilterInteger fi){
    Integer[] arr2 = new Integer[arr.length];
    int i = 0;
    for (Integer integer : arr) {
      if (fi.filter(integer)) {
        arr2[i++] = integer;
      }
    }
    return arr2;
  }

  public static void main(String[] args) {
    Integer[] arrIntegers = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    //找出大于5的数
    //[6, 7, 8, 9, 10, null, null, null, null, null]
    Integer[] arr3 = findNumbers(arrIntegers, (i) -> {return i > 5;});
    System.out.println(Arrays.asList(arr3));
    //找出偶数
    //[2, 4, 6, 8, 10, null, null, null, null, null]
    Integer[] arr4 = findNumbers(arrIntegers, (i) -> {return i%2==0;});
    System.out.println(Arrays.asList(arr4));
  }
  
}
