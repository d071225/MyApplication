package com.dyy.newtest.ui.activity.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BigBitmapActivity extends AppCompatActivity {

    @BindView(R.id.iv_matrix)
    ImageView ivMatrix;
    @BindView(R.id.iv_or)
    ImageView ivOr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_bitmap);
        ButterKnife.bind(this);
        /**
         * 获取可用堆内存和最大堆内存
         */
//        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        int memoryClass = activityManager.getMemoryClass();
//        int largeMemoryClass = activityManager.getLargeMemoryClass();
//        LogUtils.e(memoryClass + ";" + largeMemoryClass);
//        long maxMemory = Runtime.getRuntime().maxMemory();
//        long totalMemory = Runtime.getRuntime().totalMemory();
//        long freeMemory = Runtime.getRuntime().freeMemory();
//        LogUtils.e(maxMemory + ";" + totalMemory + ";" + freeMemory);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.splash_ship_xxhdpi);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        LogUtils.e(width +";"+ height +";"+bitmap.getByteCount());
        Matrix matrix=new Matrix();
        float wScale = (float) 150 / width;
        float hScale = (float) 150 / height;
//        matrix.postScale(wScale,hScale);
        matrix.postRotate(30);
//        matrix.postTranslate(width/2,height/2);
//        matrix.postSkew(0.5f,0.5f);
        Bitmap bitmap1 = bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        LogUtils.e(bitmap1.getWidth() +";"+ bitmap1.getHeight() +";"+bitmap1.getByteCount());
        ivMatrix.setImageBitmap(bitmap1);
        Matrix matrix1=new Matrix();
//        matrix1.preTranslate(-300,-300);
        matrix1.postRotate(30);
        ivOr.setImageMatrix(matrix1);
//
//        Matrix matrix2 = new Matrix();
//        matrix2.setTranslate(bitmap.getWidth() / 2, bitmap.getHeight() / 2);// 向左下平移
//        Bitmap bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
//                bitmap.getHeight(), matrix2, true);
//        ivOr.setImageBitmap(bm);



    }
}
