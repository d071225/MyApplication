package com.dyy.newtest.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.LruCache;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by DY on 2018/1/5.
 */

public class DiskLruCacheUtils {
    private static DiskLruCacheUtils diskLruCacheUtils;
    private LruCache<String,Bitmap> lruCache;//内存缓存
    private DiskLruCache diskLruCache;//磁盘缓存
    private Context context;
    public static DiskLruCacheUtils getInstance(){
        if (diskLruCacheUtils==null){
            synchronized (DiskLruCacheUtils.class){
                if (diskLruCacheUtils==null){
                    diskLruCacheUtils=new DiskLruCacheUtils();
                }
            }
        }
        return diskLruCacheUtils;
    }
    public void open(Context context,String fileName,int disCacheSize){
        try {
            this.context=context;
            int maxSize = (int) ((Runtime.getRuntime().maxMemory() / 1024) / 8);
            lruCache=new LruCache<>(maxSize);
            diskLruCache = DiskLruCache.open(getCacheDir(fileName), getAppVersion(), 1, disCacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取缓存路径
     * @param fileName
     * @return
     */
    public File getCacheDir(String fileName){
        String path= Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)||!Environment.isExternalStorageRemovable()?
                context.getExternalCacheDir().getPath():context.getCacheDir().getPath();
        return new File(path+File.separator+fileName);
    }

    /**
     * 获取App版本号
     * @return
     */
    public int getAppVersion(){
        int versionCode = 0;
        try {
            versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 刷新磁盘缓存
     */
    public void flush(){
        if (diskLruCache!=null){
            try {
                diskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 关闭磁盘缓存
     */
    public void close(){
        if (diskLruCache!=null&&!diskLruCache.isClosed()){
            try {
                diskLruCache.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 从网络上下载图片
     * @param url
     * @param callback
     */
    public void putCache(final String url, final Callback<Bitmap> callback){
        new AsyncTask<String, Void, Bitmap>() {

            private DiskLruCache.Editor edit;
            private HttpURLConnection conn;
            private BufferedInputStream is;
            private ByteArrayOutputStream baos;

            @Override
            protected Bitmap doInBackground(String... params) {
                String picUrl = params[0];
                String key = hashkeyForDisk(picUrl);
                try {
                    URL path=new URL(picUrl);
                    conn = (HttpURLConnection) path.openConnection();
                    conn.setReadTimeout(30*1000);
                    conn.setConnectTimeout(30*1000);
                    if (conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                        baos = new ByteArrayOutputStream();
                        is = new BufferedInputStream(conn.getInputStream());
                        byte[] bytes=new byte[1024];
                        int len=0;
                        while ((len= is.read(bytes))!=-1){
                            baos.write(bytes,0,len);
                        }
                        if (baos!=null){
                            Bitmap bitmap = decodeSampleadBitmapFromStream(baos.toByteArray(), 300, 300);
                            //内存内存缓存
                            addBitmapToCache(picUrl,bitmap);
                            //加入磁盘缓存
                            edit = diskLruCache.edit(key);
                            bitmap.compress(Bitmap.CompressFormat.JPEG,30,edit.newOutputStream(0));
                            edit.commit();
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    try {
                        edit.abort();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    e.printStackTrace();
                }finally {
                    try {
                        if (is!=null) is.close();
                        if (baos!=null) baos.close();
                        if (conn!=null) conn.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                callback.response(bitmap);
            }
        }.execute(url);
    }
    public interface Callback<T>{
        void response(T bitmap);
    }

    /**
     * 压缩图片
     * @param bytes
     * @param reqWidth
     * @param reqHight
     */
    public Bitmap decodeSampleadBitmapFromStream(byte[] bytes, int reqWidth, int reqHight){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;//只加载图片，不加载到内存中
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        options.inSampleSize=calculatInSampleSize(options,reqWidth,reqHight);
        options.inJustDecodeBounds=false;
        return BitmapFactory.decodeByteArray(bytes,0,bytes.length,options);
    }

    /**
     * 计算图片压缩比例
     * @param options
     * @param reqWidth
     * @param reqHight
     * @return
     */
    private int calculatInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHight) {
        int outWidth = options.outWidth;
        int outHeight = options.outHeight;
        if (outWidth>reqWidth||outHeight>reqHight){
            if (outWidth>outHeight){
                return Math.round(outHeight/reqHight);
            }else{
                return Math.round(outWidth/reqWidth);
            }
        }
        return 1;
    }

    /**
     * 添加对象到内存中
     * @param url
     * @param bitmap
     */
    public void addBitmapToCache(String url,Bitmap bitmap){
        String key = hashkeyForDisk(url);
        if (getBitmapFromCache(url)!=null){
            lruCache.put(key,bitmap);
        }
    }
    /**
     * 从磁盘里取出缓存
     * @param url
     * @return
     */
    public InputStream getDiskCache(String url){
        String key = hashkeyForDisk(url);
        try {
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (snapshot!=null){
                 return snapshot.getInputStream(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 从内存中获取缓存
     * @param url
     * @return
     */
    public Bitmap getBitmapFromCache(String url){
        String key = hashkeyForDisk(url);
        return lruCache.get(key);
    }
    /**
     * MD5加密计算
     *
     * @param key
     * @return
     */
    private String hashkeyForDisk(String key) {
        String cachekey;

        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cachekey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cachekey = String.valueOf(key.hashCode());
        }

        return cachekey;
    }
    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
