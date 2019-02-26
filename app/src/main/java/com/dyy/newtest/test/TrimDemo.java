package com.dyy.newtest.test;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DY on 2018/3/12.
 */

public class TrimDemo {
    public static void main(String[] args) {
//        Map<String,Object> map=new HashMap<>();
//        map.put("a","123 4");
//        System.out.println(trim((String) map.get("a")));
//        System.out.println(20*255/45);
//        System.out.println(getQianFenWei(Double.parseDouble("nihao")));
        String count="193456";
        System.out.println(Long.parseLong(count)/10000);
    }
    public static String trim(String str){
        if (str!=null){
            str=str.replaceAll(" ","");
        }
        return str;
    }
    public static String getQianFenWei(double num) {
        DecimalFormat dcmFmt = new DecimalFormat(",##0");
        return dcmFmt.format(num);
    }
}
