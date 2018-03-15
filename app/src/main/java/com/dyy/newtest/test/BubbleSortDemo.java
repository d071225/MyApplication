package com.dyy.newtest.test;

/**
 * Created by DY on 2018/3/9.
 */

public class BubbleSortDemo {
    public static void main(String[] args){
//        int[] arr={20,15,28,5,32,17,1,2,3,4};
        int[] arr={1,2,3,4};
        int temp;
        int count=1;
        int countOrg=1;
        boolean flag=false;
        for(int i=0;i<arr.length;i++){
            flag=false;
            for (int j=0;j<arr.length-i-1;j++){
                System.out.println("i="+i+";j="+j+";"+countOrg++);
                if (arr[j]>arr[j+1]){
                    temp=arr[j];
                    arr[j]=arr[j+1];
                    arr[j+1]=temp;
                    System.out.println("交换过一次i="+i+";j="+j+";"+count++);
                    flag=true;
                }
            }
//            if (!flag) break;//如果flag为false，说明已经排序完成，剩下的就不用排序了
        }
        for(int i=0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
    }
}
