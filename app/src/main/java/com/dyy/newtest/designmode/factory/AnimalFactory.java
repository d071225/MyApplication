package com.dyy.newtest.designmode.factory;

/**
 * Created by DY on 2018/4/4.
 */

public class AnimalFactory {
    public static Cat productCat(String name){
        return new Cat(name);
    }
    public static Dog producatDog(String name){
        return new Dog(name);
    }
}
