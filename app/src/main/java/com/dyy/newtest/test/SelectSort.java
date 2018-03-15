package com.dyy.newtest.test;

/**
 * Created by DY on 2018/3/9.
 */

public class SelectSort {

    public static void main(String[] args){
//        int[] arr={20,15,28,5,32,17,1,2,3,4};
        int[] arr={1,2,3,4};
        int min;
        int temp;
        int countOrg=1;
        int count=1;
        for (int i=0;i<arr.length-1;i++){
            min=i;
            for (int j=i+1;j<arr.length;j++){
                System.out.println("i="+i+";j="+j+";"+ countOrg++);
                if (arr[j]<arr[min]){
                    min=j;
                }
            }
            if (min!=i){
                System.out.println("交换过一次i="+i+";"+count++);
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
