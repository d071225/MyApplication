package com.dyy.newtest.designmode.factory;

/**
 * Created by DY on 2018/4/4.
 */

public class FactoryDemo {
    public static void main(String[] args) {
        Cat cat = AnimalFactory.productCat("小花");
        Dog dog = AnimalFactory.producatDog("阿黄");
        cat.eat();
        cat.move();
        dog.eat();
        dog.move();
    }
}
