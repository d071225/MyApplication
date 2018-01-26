package com.dyy.newtest.test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by DY on 2018/1/17.
 */

public class RxJava2Demo {
    public static void main(String[] args) {
//        Test1();
//        Test2();
        Test3();
    }
    public static void Test1(){
        Observable<Integer> observable=Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
//                System.out.println(e.toString());
            }
        });
        Observer<Integer> observer=new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("-----onSubscribe----"+d);
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("-----onNext----"+value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("-----onError----"+e);
            }

            @Override
            public void onComplete() {
                System.out.println("-----onComplete----");
            }
        };
        observable.subscribe(observer);
    }
    public static void Test2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
                e.onNext(4);
                System.out.println(e.toString());
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                System.out.println("-----onSubscribe----" + d);
            }

            @Override
            public void onNext(Integer value) {
                System.out.println("-----onNext----" + value);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("-----onError----" + e);
            }

            @Override
            public void onComplete() {
                System.out.println("-----onComplete----");
            }
        });
    }
    public static void Test3(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                System.out.println("Observable线程==="+Thread.currentThread().getName());
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
                e.onNext(4);
            }
        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println("subscribe线程==="+Thread.currentThread().getName());
            }
        });
    }
}
