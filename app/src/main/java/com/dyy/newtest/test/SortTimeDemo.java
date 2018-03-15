package com.dyy.newtest.test;

import com.dyy.newtest.utils.SortUtils;

import java.util.Random;

/**
 * Created by DY on 2018/3/12.
 */

public class SortTimeDemo {
    public static void main(String[] args) {
        int[] arr=new int[100000];
        Random random=new Random();
        for (int i=0;i<arr.length;i++){
            arr[i]=random.nextInt(100000);
        }
        long timeMillis = System.currentTimeMillis();
        SortUtils.bubbleSort(arr); //197757
        SortUtils.selectSort(arr); //4891
        SortUtils.insertionSort(arr); //4859
        SortUtils.quickSort(arr,0,arr.length-1); //28
        System.out.println(System.currentTimeMillis()-timeMillis);
    }
}
