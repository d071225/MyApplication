package com.dyy.newtest.test;

/**
 * Created by DY on 2018/3/30.
 */

public class SelectionSort {
    public static void main(String[] args){
        int[] arr={20,30,15,23,21};
        int temp;
        int min;
        for (int i=0;i<arr.length-1;i++){
            min=i;
            for (int j=i+1;j<arr.length;j++){
                if (arr[j]<arr[min]){
                    min=j;
                }
            }
            if (min!=i){
                temp=arr[i];
                arr[i]=arr[min];
                arr[min]=temp;
            }
        }
        for (int i=0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
    }
}
