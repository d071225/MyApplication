package com.dyy.newtest.ui.activity.EncodeActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;

import com.blankj.utilcode.util.EncodeUtils;
import com.dyy.newtest.R;

import java.io.UnsupportedEncodingException;

public class Base64Activity extends AppCompatActivity {
    private int a=123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base64);
        String str="你好";
        String encode = EncodeUtils.base64Encode2String(str.getBytes());
        System.out.println(encode);
        String decode = new String(EncodeUtils.base64Decode(encode));
        System.out.println(decode);
//        byte[] decode1 = Base64.decode("123".getBytes(), Base64.DEFAULT);
        byte[] bytes = new byte[0];
        try {
            bytes = "你好".getBytes("GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (byte b:bytes){
            System.out.print(b+" ");
        }
        String encodeA = Base64.encodeToString(str.getBytes(),Base64.DEFAULT);
        System.out.println(encodeA);
        String decodeA = new String(Base64.decode(encode,Base64.DEFAULT));
        System.out.println(decodeA);

        for (byte b:intToBytes(a)){
            System.out.print(b+" ");
        }
        String encodeInt = Base64.encodeToString(intToBytes(a),Base64.DEFAULT);
        System.out.println(encodeInt);
        String decodeInt = new String(Base64.decode(encodeInt,Base64.DEFAULT));
        System.out.println(decodeInt);
    }
    /**
     * int转byte[]
     */
    public static byte[] intToBytes(int i) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (i & 0xff);
        bytes[1] = (byte) ((i >> 8) & 0xff);
        bytes[2] = (byte) ((i >> 16) & 0xff);
        bytes[3] = (byte) ((i >> 24) & 0xff);
        return bytes;
    }
}
