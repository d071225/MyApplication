package com.dyy.newtest.test;

/**
 * Created by DY on 2018/3/12.
 */

public class QuickSortDemo {
    public static void main(String[] args){
//        int[] arr={56,18,6,3,97,66,8,26,88,30,99,93};
        int[] arr={5,7,10,8,4};
        quickSort(arr,0,arr.length-1);
        for (int i=0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
    }
    public static void quickSort(int[] arr,int l,int r){
        if (l>=r){
            return;
        }
        int p=arr[l];
        int i=l;
        int j=r;
        while (i<j){
            while (i<j&&arr[j]>=p){
                j--;
            }
            System.out.println("j="+j);
            while (i<j&&arr[i]<=p){
                i++;
            }
            System.out.println("i="+i);
            if (i<j){
                int temp=arr[i];
                arr[i]=arr[j];
                arr[j]=temp;
            }
            for (int k=0;k<arr.length;k++){
                System.out.print(arr[k]+" ");
            }
        }
        arr[l]=arr[i];
        arr[i]=p;
        for (int k=0;k<arr.length;k++){
            System.out.print(arr[k]+" ");
        }
        quickSort(arr,l,i-1);
        quickSort(arr,i+1,r);
    }
}
