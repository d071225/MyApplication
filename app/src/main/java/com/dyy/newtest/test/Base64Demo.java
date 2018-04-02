package com.dyy.newtest.test;

import com.blankj.utilcode.util.EncodeUtils;

/**
 * Created by DY on 2018/3/29.
 */

public class Base64Demo {
    public static void main(String[] args) {
        String str="123";
        String encode = EncodeUtils.base64Encode2String(str.getBytes());
        System.out.println(encode);
        String decode = new String(EncodeUtils.base64Decode(encode));
        System.out.println(decode);
    }
}
