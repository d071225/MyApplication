package com.dyy.newtest.designmode.observer;

/**
 * Created by DY on 2018/4/3.
 */

public class ObserverDemo {
    public static void main(String[] args) {
        Observer observer=new Observer("张三");
        Observer observer2=new Observer("李四");
        Observer observer3=new Observer("王五");
        Subject subject=new Subject();
        subject.addObserver(observer);
        subject.addObserver(observer2);
        subject.addObserver(observer3);
        subject.notifyObserver("回家吃饭啦！");
    }
}
