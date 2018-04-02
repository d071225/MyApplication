package com.dyy.newtest.test;

/**
 * Created by DY on 2018/3/30.
 */

public class QuickSort {
    public static void main(String[] args){
        int[] arr={20,30,15,23,20,21};
        quick(arr,0,arr.length-1);
        for (int i=0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
    }
    public static void quick(int[] arr,int left,int right){
        if (left>right)
            return;
        int point=arr[left];
        int _left=left;
        int _right=right;
        while (_left<_right){
            while (_left<_right&&arr[_right]>=point){
                _right--;
            }
            arr[_left]=arr[_right];
            while (_left<_right&&arr[_left]<=point){
                _left++;
            }
            arr[_right]=arr[_left];
        }
        arr[_left]=point;
        quick(arr,left,_left-1);
        quick(arr,_left+1,right);
    }
}
