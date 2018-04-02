package com.dyy.newtest.test;

/**
 * Created by DY on 2018/3/9.
 */

public class InsertionSortDemo {
    public static void main(String[] args){
        int[] arr={20,15,28,5,32,17};
        int temp;
        int count=1;
        for (int i=1;i<arr.length;i++){
            int j=i;
            while (j>0&&arr[j]<arr[j-1]){
                temp=arr[j-1];
                arr[j-1]=arr[j];
                arr[j]=temp;
                j--;
                System.out.println("交换过一次"+count++);
            }
        }
        for (int i=0;i<arr.length;i++){
            System.out.print(arr[i]+"");
        }
    }
}
