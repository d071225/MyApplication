package com.dyy.newtest.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.dyy.newtest.R;
import com.dyy.newtest.adapter.RefreshAdapter;
import com.dyy.newtest.bean.GirlResponse;
import com.dyy.newtest.retrofitInterface.GirlApi;
import com.dyy.newtest.utils.DiskLruCacheUtils;
import com.dyy.newtest.utils.GlideCacheUtil;
import com.dyy.newtest.view.DividerItemDecoration;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecycleViewRefreshActivity extends AppCompatActivity {

    @BindView(R.id.rv_pic)
    RecyclerView rvPic;
    @BindView(R.id.trl)
    TwinklingRefreshLayout trl;
    private DiskLruCacheUtils diskLruCacheUtils;
    private static final String DISK_FILE_NAME = "picCache";
    private static final int DISK_CACHE_SIZE = 100 * 1024 * 1024;
    private String url = "http://image.baidu.com/channel/";
    private List<String> urls;
    private List<String> headerUrls;
    private List<String> headerTitles;
    private GlideCacheUtil glideCacheUtil;
    private RefreshAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_refresh);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ButterKnife.bind(this);
        initData();
        getPicData();
        getHeaderData();
        adapter = new RefreshAdapter(RecycleViewRefreshActivity.this, headerUrls, headerTitles, urls);
        rvPic.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new RefreshAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(RecycleViewRefreshActivity.this,BigPicActivity.class);
                intent.putExtra("url",urls.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

    }

    private void initData() {
        glideCacheUtil = GlideCacheUtil.getInstance();
        urls = new ArrayList<>();
        headerUrls = new ArrayList<>();
        headerTitles = new ArrayList<>();

        rvPic.addItemDecoration(new DividerItemDecoration(RecycleViewRefreshActivity.this, DividerItemDecoration.VERTICAL_LIST, 300, 20, Color.GREEN));
        rvPic.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //设置下拉刷新动画类型
        SinaRefreshView sinaRefreshView = new SinaRefreshView(this);
        sinaRefreshView.setTextColor(Color.RED);
        sinaRefreshView.setArrowResource(R.mipmap.refresh_head_arrow);
        trl.setHeaderView(sinaRefreshView);
        trl.setBottomView(new LoadingView(this));
        trl.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        urls.clear();
                        getPicData();
                        trl.finishRefreshing();
                    }
                },2000);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPicData();
                        trl.finishLoadmore();
                    }
                },2000);
            }
        });

    }

    @OnClick({R.id.rv_pic})
    public void onViewClicked() {
        glideCacheUtil.clearImageAllCache(RecycleViewRefreshActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        diskLruCacheUtils = DiskLruCacheUtils.getInstance();
        diskLruCacheUtils.open(this, DISK_FILE_NAME, DISK_CACHE_SIZE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        diskLruCacheUtils.flush();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        diskLruCacheUtils.close();
    }

    public void getPicData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GirlApi girlApi = retrofit.create(GirlApi.class);
        Call<GirlResponse> call = girlApi.getResult();
        call.enqueue(new Callback<GirlResponse>() {
            @Override
            public void onResponse(Call<GirlResponse> call, Response<GirlResponse> response) {
                List<GirlResponse.DesData> datas = response.body().getData();
                for (GirlResponse.DesData desData : datas) {
                    String image_url = desData.getImage_url();
//                    Log.e("BitmapActivity", "标题:" + desData.getAbs() + ";地址:" + image_url);
                    urls.add(image_url);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GirlResponse> call, Throwable t) {

            }
        });
    }

    public void getHeaderData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GirlApi girlApi = retrofit.create(GirlApi.class);
        Call<GirlResponse> call = girlApi.getHeaderResult();
        call.enqueue(new Callback<GirlResponse>() {
            @Override
            public void onResponse(Call<GirlResponse> call, Response<GirlResponse> response) {
                List<GirlResponse.DesData> datas = response.body().getData();
                for (GirlResponse.DesData desData : datas) {
                    String image_url = desData.getImage_url();
                    String abs = desData.getAbs();
//                    Log.e("BitmapActivity", "标题:" + desData.getAbs() + ";地址:" + image_url);

                    headerUrls.add(image_url);
                    Log.e("", "headerTitles" + headerTitles.isEmpty());
                    headerTitles.add(abs);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<GirlResponse> call, Throwable t) {

            }
        });
    }
}
