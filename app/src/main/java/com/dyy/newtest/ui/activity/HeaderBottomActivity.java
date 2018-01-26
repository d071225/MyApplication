package com.dyy.newtest.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dyy.newtest.R;
import com.dyy.newtest.adapter.HeaderBottomAdapter;
import com.dyy.newtest.bean.GirlResponse;
import com.dyy.newtest.retrofitInterface.GirlApi;
import com.dyy.newtest.utils.DiskLruCacheUtils;
import com.dyy.newtest.utils.GlideCacheUtil;
import com.dyy.newtest.view.DividerItemDecoration;

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

public class HeaderBottomActivity extends AppCompatActivity {

    @BindView(R.id.rv_pic)
    RecyclerView rvPic;
    @BindView(R.id.tv_cache_size)
    TextView tvCacheSize;
    @BindView(R.id.btn_clean_cache)
    Button btnCleanCache;
    private DiskLruCacheUtils diskLruCacheUtils;
    private static final String DISK_FILE_NAME = "picCache";
    private static final int DISK_CACHE_SIZE = 100 * 1024 * 1024;
    private String url = "http://image.baidu.com/channel/";
    private List<String> urls;
    private List<String> headerUrls;
    private List<String> headerTitles;
    private GlideCacheUtil glideCacheUtil;
    private HeaderBottomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        ButterKnife.bind(this);
        initData();
        getPicData();
        getHeaderData();
        adapter = new HeaderBottomAdapter(HeaderBottomActivity.this,headerUrls,headerTitles,urls);
        rvPic.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new HeaderBottomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(HeaderBottomActivity.this,BigPicActivity.class);
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
        final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        //item跳动
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        //万能分割线
        rvPic.addItemDecoration(new DividerItemDecoration(HeaderBottomActivity.this, 1, 1, R.color.colorAccent));
        rvPic.setLayoutManager(staggeredGridLayoutManager);
        //顶部有空白
        rvPic.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                staggeredGridLayoutManager.invalidateSpanAssignments();
            }
        });
        tvCacheSize.setText("图片缓存大小:"+glideCacheUtil.getCacheSize(HeaderBottomActivity.this));

    }

    @OnClick({R.id.rv_pic, R.id.btn_clean_cache})
    public void onViewClicked() {
        glideCacheUtil.clearImageAllCache(HeaderBottomActivity.this);
        tvCacheSize.setText("图片缓存大小:"+glideCacheUtil.getCacheSize(HeaderBottomActivity.this));
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
                    Log.e("","headerTitles"+headerTitles.isEmpty());
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
