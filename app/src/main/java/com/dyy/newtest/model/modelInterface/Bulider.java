package com.dyy.newtest.model.modelInterface;

import android.widget.TextView;

/**
 * Created by DY on 2018/1/15.
 * 建造者模式
 */

public abstract class Bulider {
    public abstract void setMyTextSize(int size);
    public abstract void setMyTextColor(int color);
    public abstract void setMyText(String content);
    public abstract TextView create();
}
