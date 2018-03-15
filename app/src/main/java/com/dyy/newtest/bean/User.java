package com.dyy.newtest.bean;

import android.support.annotation.IntRange;
import android.support.annotation.Size;

import java.io.Serializable;

/**
 * Created by DY on 2018/1/3.
 */

public class User implements Serializable {
    private static final long serialVersionUID = 1755008056002793916L;
    private String name;
    private int age;
    private String password;
    public static int count=1;
    public String getName() {
        return name;
    }
    public void setName(@Size(min = 3) String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(@IntRange(from=0,to=150) int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@Size(max = 20) String password) {
        this.password = password;
    }
}
