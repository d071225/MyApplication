package com.dyy.newtest.designmode.factory;

/**
 * Created by DY on 2018/4/4.
 */

public class Cat implements Animal {
    private String name;
    public Cat(String name) {
        this.name=name;
    }

    @Override
    public void move() {
        System.out.println(name+"正在溜达。。。");
    }

    @Override
    public void eat() {
        System.out.println(name+"正在吃。。。");
    }
}
