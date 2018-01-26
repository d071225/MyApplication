package com.dyy.newtest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.bean.ApiResponse;
import com.dyy.newtest.bean.Province;
import com.dyy.newtest.retrofitInterface.WxApi;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxJava2Activity extends AppCompatActivity {
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.btn_request)
    Button btnRequest;
    @BindView(R.id.btn_stop)
    Button btnStop;
    private Disposable disposable;
    private Subscription subscription;
    private StringBuilder sb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java2);
        ButterKnife.bind(this);
        sb=new StringBuilder();
        tvCity.setMovementMethod(ScrollingMovementMethod.getInstance());
        EventBus.getDefault().register(this);
//        Test3();
//        getData();
//        Test4();
//        Test5();
//        Test6();
//        Test7();
//        Test8();
        Test9();
//        Test10();
    }

    /**
     * 实战 读取文件
     */
    public void Test10(){
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
//                FileReader fw=new FileReader("C:\\Users\\DY\\Downloads\\《曾国藩家书》全集.txt");
//                FileReader fw=new FileReader(RxJava2Activity.this.getCacheDir()+ File.separator+"zgf.txt");
//                LogUtils.e("编码格式："+fw.getEncoding());
                BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(RxJava2Activity.this.getCacheDir()+ File.separator+"zgf.txt"),"GBK"));
                String lin=null;
                while ((lin=br.readLine())!=null){
                    while (e.requested()==0){
                        if (e.isCancelled()){
                            break;
                        }
                    }
                    e.onNext(lin);
//                    LogUtils.e("subscribe"+lin);
                }
            }
        },BackpressureStrategy.ERROR).subscribeOn(Schedulers.io())
        .observeOn(Schedulers.newThread())
        .subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(10);
                subscription=s;
                LogUtils.e("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                try {
                    Thread.sleep(100);
                    System.out.println(s);
//                    System.out.println(sb);
//                    tvCity.setText(s);
//                    subscription.request(1);
                    sb.append(s);
                    sb.append("/r");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable t) {
                LogUtils.e("onError "+t);
            }

            @Override
            public void onComplete() {
                LogUtils.e("onComplete");
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setContent(String content){
        tvCity.setText(content);
    }
    /**
     *  异步处理requested
     */
    public void Test9(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                boolean flag;
                for (int i=0;i<10000;i++) {
                    flag=false;
//                    while (e.requested()==0){
//                        if (!flag){
//                            LogUtils.a("下游处理能力为0，不再发送事件");
//                            flag=true;
//                        }
//                    }
                    while (e.requested()==0){
                        if (e.isCancelled()){
                            LogUtils.e("subscribe Emitter isCancelled");
                            break;
                        }
                    }
                    e.onNext(i);
//                    LogUtils.e("下游处理能力：" + e.requested()+";当前线程："+Thread.currentThread().getName());
                }
                e.onComplete();
            }
        },BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        subscription=s;
                        s.request(50);
                        LogUtils.e("onSubscribe 当前线程:"+Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        try {
                            System.out.println(integer);
                            Thread.sleep(100);
                            sb.append(integer);
                            sb.append("\n");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.e("onError:"+t);
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.e("onComplete:");
                        EventBus.getDefault().post(sb.toString());
                    }
                });
    }
    /**
     *  同步处理requested
     */
    public void Test8(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                for (int i=0;i<10;i++) {
                    e.onNext(i);
                    LogUtils.e("下游处理能力：" + e.requested());
                }
            }
        },BackpressureStrategy.ERROR)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(10);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        System.out.println(integer);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     *  操作符方法背压策略onBackpressureDrop
     */
    public void Test7(){
        Flowable.interval(1,TimeUnit.MILLISECONDS)
                .onBackpressureDrop()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                        LogUtils.e("当前线程："+Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(Long aLong) {
//                        try {
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        System.out.println("onNext---》"+aLong);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.e("onComplete---》" + t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    /**
     * Flowable BackpressureStrategy.LATEST
     */
    public void Test6() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                for (int i = 0; i<1000; i++) {
                    e.onNext(i);
                }
//                e.onNext(1);
//                LogUtils.a("emitter 1");
//                e.onNext(2);
//                LogUtils.a("emitter 2");
//                e.onNext(3);
//                LogUtils.a("emitter 3");
//                e.onNext(4);
//                LogUtils.a("emitter 4");
//                e.onComplete();
//                LogUtils.a("onComplete");
            }
        }, BackpressureStrategy.LATEST)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        LogUtils.e("onSubscribe---》");
                        s.request(128);
                        subscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
//                LogUtils.e("onNext 消费---》"+integer);
                        System.out.println("消费:" + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.e("onComplete---》" + t);
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.e("onComplete---》");
                    }
                });
    }

    /**
     * zip
     */
    public void Test5() {
//        int[] nums={1,2,3,4};
//        String[] letters={"A","B","C"};
//        Flowable observable1=Flowable.fromIterable(Arrays.asList(nums));
//        Flowable observable2=Flowable.fromIterable(Arrays.asList(letters));
//        Flowable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
//            @Override
//            public String apply(Integer integer, String s) throws Exception {
//                return integer+s;
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String o) throws Exception {
//                System.out.println("zip:"+o);
//            }
//        });
        Observable observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                e.onNext("A");
//                e.onNext("B");
//                e.onNext("C");
                while (true) {
                    e.onNext("A");
                }
            }
        });
        Observable observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onNext(4);
            }
        });
        Observable.zip(observable2, observable1, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
                System.out.println("zip:" + o);
            }
        });

    }

    /**
     * flatmap 和 concatmap
     */
    public void Test4() {
        final List<Province> provinces = new ArrayList<>();
        List<Province.City> jsCities = new ArrayList<>();
        List<Province.City> shhCities = new ArrayList<>();
        List<Province.City> zhjCities = new ArrayList<>();
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
        provinces.add(new Province("江苏", jsCities));
        provinces.add(new Province("上海", shhCities));
        provinces.add(new Province("浙江", zhjCities));
        Flowable.fromIterable(provinces)
                .concatMap(new Function<Province, Publisher<Province.City>>() {
                    @Override
                    public Publisher<Province.City> apply(Province province) throws Exception {
                        LogUtils.e("省份:" + province.getProName());
//                        int delay=0;
//                        if (province.getProName().equals("上海")){
//                            delay=500;
//                        }
                        return Flowable.fromIterable(province.getCity()).delay(5000, TimeUnit.MILLISECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Province.City>() {
                    @Override
                    public void accept(Province.City city) throws Exception {
                        LogUtils.e("城市:" + city.getCityName());
//                        Toast.makeText(RxJava2Activity.this, "城市:" + city.getCityName(), Toast.LENGTH_SHORT).show();
                        tvCity.setText("城市:" + city.getCityName());
                        if (city.getCityName().equals("杭州")) {
                            finish();
                        }
                    }
                });
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {

            }
        });
    }

    public void getData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.10.110:8380/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        WxApi wxApi = retrofit.create(WxApi.class);
        Map<String, String> map = new TreeMap<>();
        map.put("app_id", "wx68a4cf17e7f4096b");
        map.put("msg_content", "{\"first\": {\"value\":\"恭喜你购买成功！\",\"color\":\"#173177\"},\"keynote1\":{\"value\":\"巧克力\",\"color\":\"#173177\"},\"keynote2\": {\"value\":\"39.8元\",\"color\":\"#173177\"},\"keynote3\": {\"value\":\"2014年9月22日\",\"color\":\"#173177\"},\"remark\":{\"value\":\"欢迎再次购买！\",\"color\":\"#173177\"}}");
        map.put("template_id", "ZldEcxWNYP0-0AmTuRJDGVQDL1-xJhBp4yt-ouyRq4I");
        map.put("sys_id", "PA001");
        map.put("mcht_msg_no", "20180104");
        map.put("sign_type", "01");
        map.put("sign", "145aeda963a2f8dd44a3071e8f6ec4db");
//        WxRequest request=new WxRequest();
//        request.setApp_id("123");
//        Call<ApiResponse> resultCall = wxApi.getResult(map);
        wxApi.getRxResult(map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ApiResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.e("-------onSubscribe-------");
                        disposable = d;
                    }

                    @Override
                    public void onNext(ApiResponse value) {
                        LogUtils.e("-------onNext-------");
                        String message = value.getMessage();
                        Toast.makeText(RxJava2Activity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("-------onError-------");
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.e("-------onComplete-------");
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        disposable.dispose();
    }

    public void Test3() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                System.out.println("Observable线程===" + Thread.currentThread().getName());
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
                e.onNext(4);
            }
        })
                .subscribeOn(Schedulers.newThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("before-Observable-doOnNext" + Thread.currentThread().getName());
                    }
                })
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("after-Observable-doOnNext" + Thread.currentThread().getName());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("before subscribe doOnNext" + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("after subscribe doOnNext" + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println("subscribe线程===" + Thread.currentThread().getName());
                    }
                });
    }


    @OnClick({R.id.btn_request, R.id.tv_city,R.id.btn_stop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_request:
                subscription.request(10);
                break;
            case R.id.tv_city:
                break;
            case R.id.btn_stop:
                subscription.cancel();
                break;
        }
    }

}
