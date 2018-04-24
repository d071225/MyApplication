package com.dyy.newtest.designmode.observer;

/**
 * Created by DY on 2018/4/3.
 */

public class Observer implements ObserverInterface {
    private String name;
    public Observer(String name) {
        this.name=name;
    }

    @Override
    public void update(String msg) {
        System.out.println(name+msg);
    }
}
