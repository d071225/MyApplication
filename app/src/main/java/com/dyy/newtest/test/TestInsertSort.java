package com.dyy.newtest.test;

/**
 * Created by DY on 2018/3/12.
 */

public class TestInsertSort {
    public static void main(String[] args){
        int[] arr={8,1,6,7,2,4,5,3};
        int j;
        for (int i=1;i<arr.length;i++){
            j=i;
            while (j>0&&arr[j]<arr[j-1]){
                int temp=arr[j];
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
