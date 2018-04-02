package com.dyy.newtest.test;

/**
 * Created by DY on 2018/3/30.
 */

public class BubbleSort {
    public static void main(String[] args){
        int[] arr={20,30,15,23,21};
        int temp;
        for (int i=0;i<arr.length;i++){
            for (int j=0;j<arr.length-i-1;j++){
                if (arr[j]<arr[j+1]){
                    temp=arr[j+1];
                    arr[j+1]=arr[j];
                    arr[j]=temp;
                }
            }
        }
        for (int i=0;i<arr.length;i++)
        System.out.println(arr[i]);
    }
}
