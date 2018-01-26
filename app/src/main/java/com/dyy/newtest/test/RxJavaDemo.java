package com.dyy.newtest.test;

import com.dyy.newtest.bean.House;
import com.dyy.newtest.bean.Province;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by DY on 2018/1/16.
 */

public class RxJavaDemo {
    public static void main(String[] args) {
        /**
         * map
         */
//        Observable.just(1,2,3,4,5)
//                .map(new Func1<Integer, String>() {
//                    @Override
//                    public String call(Integer integer) {
//                        return "RxJava demo:"+integer;
//                    }
//                }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                System.out.println(s);
//            }
//        });
        /**
         * groupBy
         */
        List<House> houses=new ArrayList<>();
        houses.add(new House("中粮·海景壹号", "中粮海景壹号新出大平层！总价4500W起"));
        houses.add(new House("竹园新村", "满五唯一，黄金地段"));
        houses.add(new House("中粮·海景壹号", "毗邻汤臣一品"));
        houses.add(new House("竹园新村", "顶层户型，两室一厅"));
        houses.add(new House("中粮·海景壹号", "南北通透，豪华五房"));
//        Observable<GroupedObservable<String,House>> groupedObservableObservable=Observable
//                .from(houses)
//                .groupBy(new Func1<House, String>() {
//                    @Override
//                    public String call(House house) {
//                        return house.getName();
//                    }
//                });
//        Observable.concat(groupedObservableObservable)
//                .subscribe(new Action1<House>() {
//                    @Override
//                    public void call(House house) {
//                        System.out.println("小区："+house.getName()+";房源描述:"+house.getDes());
//                    }
//                });
//        Observable.from(houses)
//                .map(new Func1<House, String>() {
//                    @Override
//                    public String call(House house) {
//                        return house.getName();
//                    }
//                })
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        System.out.println(s);
//                    }
//                });
        /**
         * flatmap
         */
//        final List<Province> provinces=new ArrayList<>();
//        List<Province.City> jsCities=new ArrayList<>();
//        List<Province.City> shhCities=new ArrayList<>();
//        List<Province.City> zhjCities=new ArrayList<>();
//        jsCities.add(new Province.City("南京"));
//        jsCities.add(new Province.City("苏州"));
//        jsCities.add(new Province.City("无锡"));
//        jsCities.add(new Province.City("盐城"));
//        shhCities.add(new Province.City("静安"));
//        shhCities.add(new Province.City("闸北"));
//        shhCities.add(new Province.City("闵行"));
//        shhCities.add(new Province.City("嘉定"));
//        zhjCities.add(new Province.City("杭州"));
//        zhjCities.add(new Province.City("温州"));
//        zhjCities.add(new Province.City("宁波"));
//        zhjCities.add(new Province.City("嘉兴"));
//        provinces.add(new Province("江苏",jsCities));
//        provinces.add(new Province("上海",shhCities));
//        provinces.add(new Province("浙江",zhjCities));
//        Observable.from(provinces)
//                .flatMap(new Func1<Province, Observable<Province.City>>() {
//
//                    @Override
//                    public Observable<Province.City> call(Province province) {
//                        System.out.println("省份:"+province.getProName());
//                        if (province.getProName().equals("上海")){
//                            try {
//                                Thread.sleep(500);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        return Observable.from(province.getCity());
//                    }
//                })
//                .subscribe(new Action1<Province.City>() {
//                    @Override
//                    public void call(Province.City city) {
//                        System.out.println("城市名:"+city.getCityName());
//                    }
//                });
        /**
         * concatMap 无效
         */
//        Observable.just(1,2,3,4,5)
//                .concatMap(new Func1<Integer, Observable<Integer>>() {
//
//                    @Override
//                    public Observable<Integer> call(Integer integer) {
//                        int delay=0;
//                        if (integer==2){
//                            delay=100;
//                        }
//                        return Observable.just(integer*10).delay(delay, TimeUnit.MILLISECONDS);
//                    }
//                })
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.println(integer);
//                    }
//                });
        /**
         * filter
         */
        final List<Province> provinces=new ArrayList<>();
        List<Province.City> jsCities=new ArrayList<>();
        List<Province.City> shhCities=new ArrayList<>();
        List<Province.City> zhjCities=new ArrayList<>();
        jsCities.add(new Province.City("南京"));
        jsCities.add(new Province.City("苏州"));
        jsCities.add(new Province.City("无锡"));
        jsCities.add(new Province.City("盐城"));
        jsCities.add(new Province.City("淮安"));
        shhCities.add(new Province.City("静安"));
        shhCities.add(new Province.City("闸北"));
        shhCities.add(new Province.City("闵行"));
        shhCities.add(new Province.City("嘉定"));
        zhjCities.add(new Province.City("杭州"));
        zhjCities.add(new Province.City("温州"));
        zhjCities.add(new Province.City("宁波"));
        zhjCities.add(new Province.City("嘉兴"));
        provinces.add(new Province("江苏",jsCities));
        provinces.add(new Province("上海",shhCities));
        provinces.add(new Province("浙江",zhjCities));
//        Observable.from(provinces)
//                .filter(new Func1<Province, Boolean>() {
//                    @Override
//                    public Boolean call(Province province) {
//                        return province.getCity().size()>4;
//                    }
//                })
//                .flatMap(new Func1<Province, Observable<Province.City>>() {
//                    @Override
//                    public Observable<Province.City> call(Province province) {
//                        System.out.println(province.getProName());
//                        return Observable.from(province.getCity());
//                    }
//                })
//                .subscribe(new Action1<Province.City>() {
//                    @Override
//                    public void call(Province.City city) {
//                        System.out.println(city.getCityName());
//                    }
//                });
        /**
         * take
         */
//        Observable.from(jsCities)
//                .take(2)
//                .subscribe(new Action1<Province.City>() {
//                    @Override
//                    public void call(Province.City city) {
//                        System.out.println(city.getCityName());
//                    }
//                });
        /**
         * take
         */
//        Observable.from(jsCities)
//                .takeLast(2)
//                .subscribe(new Action1<Province.City>() {
//                    @Override
//                    public void call(Province.City city) {
//                        System.out.println(city.getCityName());
//                    }
//                });
        System.out.println("---------------------------------");
        /**
         * takeUntil
         */
//        Observable.just(1,2,3,4,5)
//                .takeUntil(new Func1<Integer, Boolean>() {
//                    @Override
//                    public Boolean call(Integer integer) {
//                        return integer>=3;
//                    }
//                })
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.println(integer);
//                    }
//                });
        System.out.println("---------------------------------");
        /**
         * skip
         */
//        Observable.just(1,2,3,4,5)
//                .skip(2)
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.println(integer);
//                    }
//                });
        System.out.println("---------------------------------");
        /**
         * skipLast
         */
//        Observable.just(1,2,3,4,5)
//                .skipLast(2)
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.println(integer);
//                    }
//                });
        System.out.println("---------------------------------");
        /**
         * elementAt
         */
//        Observable.just(1,3,5,7,9)
//                .elementAt(2)
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.println(integer);
//                    }
//                });
        System.out.println("---------------------------------");
        /**
         * distinct
         */
//        Observable.just(1,1,3,5,7,9,4,5,3,2)
//                .distinct()
//                .subscribe(new Action1<Integer>() {
//                    @Override
//                    public void call(Integer integer) {
//                        System.out.println(integer);
//                    }
//                });
        System.out.println("---------------------------------");
        /**
         * distinct(fun)
         */
//        Observable.from(houses)
//                .distinct(new Func1<House, String>() {
//                    @Override
//                    public String call(House house) {
//                        return house.getName();
//                    }
//                })
//                .subscribe(new Action1<House>() {
//                    @Override
//                    public void call(House house) {
//                        System.out.println(house.getName()+";"+house.getDes());
//                    }
//                });
        System.out.println("------------组合操作---------------------");
        /**
         * merge
         */
        final String[] letters={"a","b","c","d","e","f"};
//        Observable<String> lettersObservable= (Observable<String>) Observable.timer(300,TimeUnit.MILLISECONDS)
//                .map(new Func1<Long, String>() {
//                    @Override
//                    public String call(Long aLong) {
//                        return letters[aLong.intValue()];
//                    }
//                })
//                .take(letters.length)
//                .subscribe(new Action1<String>() {
//                    @Override
//                    public void call(String s) {
//                        System.out.println(s);
//                    }
//                });
//        Observable<Long> numLongObservable=Observable.timer(500,TimeUnit.MILLISECONDS).take(5);
//        Observable.merge(lettersObservable,numLongObservable)
//                .subscribe(new Observer<Serializable>() {
//                    @Override
//                    public void onCompleted() {
//                        System.out.println("onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println("onError:"+e);
//                    }
//
//                    @Override
//                    public void onNext(Serializable serializable) {
//                        System.out.print(serializable.toString()+" ");
//                    }
//                });
        System.out.println("------------组合操作---------------------");
        /**
         *
         */
//        Observable<Integer> observable1 = Observable.just(1, 2, 3);
//        Observable<Integer> observable2 = Observable.just(11, 22, 33);
//
//        Observable.merge(observable1, observable2)
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onCompleted() {
//                        System.out.println("onCompleted");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        System.out.println("onError");
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        System.out.println("i = " + integer);
//                    }
//                });

    }
}
