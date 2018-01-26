package com.dyy.newtest.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.dyy.newtest.R;
import com.dyy.newtest.utils.DiskLruCacheUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DY on 2018/1/5.
 */

public class GirlAdapter extends RecyclerView.Adapter<GirlAdapter.GirlVHold> {
    private static final String TAG="GirlAdapter";
    private final ArrayList<Integer> heightList;
    private Context context;
    private List<String> urls;
    private DiskLruCacheUtils diskLruCacheUtils;
    private final int width;
    private HashMap<Integer,Integer> imageHeightMap;

    public GirlAdapter(Context context, List<String> urls, DiskLruCacheUtils diskLruCacheUtils) {
        this.context = context;
        this.urls = urls;
        this.diskLruCacheUtils = diskLruCacheUtils;
        //计算item的宽度
//        itemWidth = ScreenUtils.getScreenWidth()/4;
        /**
         * 方案二
         */
        heightList = new ArrayList<>();
        for (int i = 0; i < urls.size() ; i++) {
            int height = (int) (Math.random()*400 + 400);
            heightList.add(height);
        }
        /**
         * 方案三
         */
        //屏幕的宽度(px值）
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;;
        //Item的宽度，或图片的宽度
        width = screenWidth/4;
        /**
         * 方案四
         */
        imageHeightMap=new HashMap<>();
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public GirlVHold onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType==1){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pic_item,parent,false);
        return new GirlVHold(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(GirlVHold holder, int position) {
        //利用diskLruCache经行缓存
//        holder.girl.setTag(urls.get(position));
//        loadBitmap(urls.get(position),holder.girl);

        ImageView girl = holder.girl;
        String url = urls.get(position);

        //方案二：改变holder.button的高度
//        int height = heightList.get(position);
//        Log.e("GirlAdapter","url:"+url+"---------------随机高度:"+height);
//        ViewGroup.LayoutParams lp = girl.getLayoutParams();
//        lp.height = height;
//        lp.width = width;
//        girl.setLayoutParams(lp);
//        Glide.with(context).load(urls.get(position)).placeholder(R.mipmap.ic_launcher).fitCenter().into(girl);

        //方案三
//        Glide.with(context).load(url).placeholder(R.mipmap.ic_launcher).override(width, SIZE_ORIGINAL).fitCenter().into(girl);
        Log.e("GirlAdapter","url:"+url+";高度:"+girl.getHeight());

        //方案四
        if (!this.imageHeightMap.containsKey(position)){
            //当首次加载图片时，调用 loadImageFirst()，保存图片高度
            loadImageFirst(girl,position);
        }else{
            //非首次加载，直接根据保存的长宽，获取图片
            Glide.with(context)
                    .load(urls.get(position)).override(width,this.imageHeightMap.get(position))
                    .into(girl);
        }
    }

    public void loadImageFirst(View view, final int position){
        //构造方法中参数view,就是回调方法中的this.view
        ViewTarget<View,Bitmap> target = new ViewTarget<View, Bitmap>(view) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                //加载图片成功后调用
                float scaleType = ((float) resource.getHeight())/resource.getWidth();
                int imageHeight = (int) (width*scaleType);
                //获取图片高度，保存在Map中
                imageHeightMap.put(position,imageHeight);
                //设置图片布局的长宽，Glide会根据布局的自动加载适应大小的图片
                ViewGroup.LayoutParams lp = this.view.getLayoutParams();
                lp.width=width;
                lp.height=imageHeight;
                this.view.setLayoutParams(lp);
                //resource就是加载成功后的图片资源
                ((ImageView)view).setImageBitmap(resource);
            }
            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                //加载图片失败后调用
                super.onLoadFailed(e, errorDrawable);
                int imageHeight = width;
                imageHeightMap.put(position,imageHeight);
                ViewGroup.LayoutParams lp = this.view.getLayoutParams();
                lp.width=width;
                lp.height=imageHeight;
                this.view.setLayoutParams(lp);
                ((ImageView)view).setImageResource(R.mipmap.ic_launcher);
            }
        };
        Glide.with(context)
                .load(urls.get(position))
                .asBitmap()                 //作为Bitmap加载，对应onResourceReady回调中第一个参数的类型
                .into(target);
    }


    private void loadBitmap(String url, final ImageView iv) {
        if (iv.getTag().equals(url)){
            //从内存中取出缓存
            Bitmap bitmapFromCache = diskLruCacheUtils.getBitmapFromCache(url);
            if (bitmapFromCache==null){
                //从磁盘里取出缓存
                InputStream diskCache = diskLruCacheUtils.getDiskCache(url);
                if (diskCache==null){
                    //从网络上下载
                    Log.e(TAG,"从网上下载。。。");
                    diskLruCacheUtils.putCache(url, new DiskLruCacheUtils.Callback<Bitmap>() {
                        @Override
                        public void response(Bitmap bitmap) {
                            iv.setImageBitmap(bitmap);
                        }
                    });
                }else {
                    Log.e(TAG,"从磁盘里取出。。。");
                    //磁盘里取出的缓存放到内存中
                    Bitmap bitmap = BitmapFactory.decodeStream(diskCache);
                    diskLruCacheUtils.addBitmapToCache(url,bitmap);
                    iv.setImageBitmap(bitmap);
                }
            }else {
                Log.e(TAG,"从内存中取出。。。");
                iv.setImageBitmap(bitmapFromCache);
            }
        }
    }

    @Override
    public int getItemCount() {
        return urls==null?0:urls.size();
    }

    public class GirlVHold extends RecyclerView.ViewHolder{
        private ImageView girl;
        public GirlVHold(View itemView) {
            super(itemView);
            girl= (ImageView) itemView.findViewById(R.id.iv_pic);
        }
    }
}
