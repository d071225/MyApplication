package com.dyy.newtest.model;

import com.dyy.newtest.model.modelInterface.Observer;
import com.dyy.newtest.model.modelInterface.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DY on 2018/1/15.
 */

public class AllUser implements Subject {
    private List<Observer> observers=new ArrayList<>();
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void dettach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notify(String msg) {
        for (Observer observer:observers){
            observer.update(msg);
        }
    }
}
