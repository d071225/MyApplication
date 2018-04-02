package com.dyy.newtest.test;

/**
 * Created by DY on 2018/3/30.
 */

public class InsertionSort {
    public static void main(String[] args){
        int[] arr={20,30,15,23,21};
        int j;
        int temp;
        for (int i=1;i<arr.length;i++){
            j=i;
            while (j>0&&arr[j]<arr[j-1]){
                temp=arr[j];
                arr[j]=arr[j-1];
                arr[j-1]=temp;
                j--;
            }
        }
        for (int i=0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
    }
}
