package com.dyy.newtest.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DY on 2018/3/12.
 */

public class TrimDemo {
    public static void main(String[] args) {
        Map<String,Object> map=new HashMap<>();
        map.put("a","123 4");
        System.out.println(trim((String) map.get("a")));
    }
    public static String trim(String str){
        if (str!=null){
            str=str.replaceAll(" ","");
        }
        return str;
    }
}
