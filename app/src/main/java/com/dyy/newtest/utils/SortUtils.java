package com.dyy.newtest.utils;

/**
 * Created by DY on 2018/3/12.
 */

public  class SortUtils {
    /**
     * 快速排序
     * @param arr 目标数组
     * @param l 最左边下标
     * @param r 最右边下标
     */
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
            while (i<j&&arr[i]<=p){
                i++;
            }
            if (i<j){
                int temp=arr[i];
                arr[i]=arr[j];
                arr[j]=temp;
            }
        }
        arr[l]=arr[i];
        arr[i]=p;
        quickSort(arr,l,i-1);
        quickSort(arr,i+1,r);
    }

    /**
     * 冒泡排序
     * @param arr
     */
    public static void bubbleSort(int[] arr){
        int temp;
        boolean flag=false;
        for(int i=0;i<arr.length;i++){
            flag=false;
            for (int j=0;j<arr.length-i-1;j++){
                if (arr[j]>arr[j+1]){
                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                    flag=true;
                }
            }
            if (!flag) break;//如果flag为false，说明已经排序完成，剩下的就不用排序了
        }
    }

    /**
     * 插入排序
     * @param arr
     */
    public static void insertionSort(int[] arr){
        int temp;
        for (int i=1;i<arr.length;i++){
            int j=i;
            while (j>0&&arr[j]<arr[j-1]){
                temp=arr[j-1];
                arr[j-1]=arr[j];
                arr[j]=temp;
                j--;
            }
        }
    }

    /**
     * 选择排序
     * @param arr
     */
    public static void selectSort(int[] arr){
        int min;
        int temp;
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
    }
}
