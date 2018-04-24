package com.dyy.newtest.designmode.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DY on 2018/4/3.
 */

public class Subject implements SubjectInterface {
    private List<Observer> mObservers=new ArrayList<>();
    @Override
    public void addObserver(Observer observer) {
        mObservers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        mObservers.remove(observer);
    }

    @Override
    public void notifyObserver(String msg) {
        for (Observer obs:mObservers){
            obs.update(msg);
        }
    }
}
