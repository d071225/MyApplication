package com.dyy.newtest.view;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;
import com.dyy.newtest.utils.OkHttpGlideUrlLoader;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * Created by DY on 2018/1/12.
 */

public class MyGlideModule implements GlideModule {
    public static final String CACHE_NAME="big_pic";
    public static final int CACHE_SIZE=100*1024*1024;
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator=new MemorySizeCalculator(context);
        int bitmapPoolSize = calculator.getBitmapPoolSize();
        int memoryCacheSize = calculator.getMemoryCacheSize();
        int custBitmapPoolSize = (int) (1.2 * bitmapPoolSize);
        int custMemoryCacheSize = (int) (1.2 * memoryCacheSize);
        builder.setBitmapPool(new LruBitmapPool(custBitmapPoolSize));
        builder.setMemoryCache(new LruResourceCache(custMemoryCacheSize));
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context,CACHE_NAME,CACHE_SIZE));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.addInterceptor(new ProgressInterceptor());
        OkHttpClient okHttpClient = builder.build();
        glide.register(GlideUrl.class, InputStream.class, new OkHttpGlideUrlLoader.Factory(okHttpClient));
    }
}
