package com.dyy.newtest.model.modelInterface;

/**
 * Created by DY on 2018/1/15.
 */

public interface Subject {
    public void attach(Observer observer);
    public void dettach(Observer observer);
    public void notify(String msg);
}
