package com.dyy.newtest.designmode.factory;

/**
 * Created by DY on 2018/4/4.
 */

public class Dog implements Animal {
    private String name;

    public Dog(String name) {
        this.name = name;
    }

    @Override
    public void move() {
        System.out.println(name+"正在追逐。。。");
    }

    @Override
    public void eat() {
        System.out.println(name+"正在吃骨头。。。");
    }
}
