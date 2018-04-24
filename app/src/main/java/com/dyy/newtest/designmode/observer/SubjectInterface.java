package com.dyy.newtest.designmode.observer;

/**
 * Created by DY on 2018/4/3.
 */

public interface SubjectInterface {
    public void addObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObserver(String msg);
}
