package com.dyy.newtest.jni;

/**
 * Created by DY on 2018/4/4.
 */

public class JniTest {
    static {
        System.loadLibrary("native-lib");
    }
    public static native String getJniString();
    public static native int add(int i,int j);
}