package com.dyy.newtest.model;

import android.widget.TextView;

import com.dyy.newtest.model.modelInterface.Bulider;

/**
 * Created by DY on 2018/1/15.
 * 建造者模式
 */

public class TextViewBulider extends Bulider {
    private TextView textView;

    public TextViewBulider(TextView textView) {
        this.textView = textView;
    }

    @Override
    public void setMyTextSize(int size) {
        textView.setTextSize(size);
    }

    @Override
    public void setMyTextColor(int color) {
        textView.setTextColor(color);
    }

    @Override
    public void setMyText(String content) {
        textView.setText(content);
    }

    @Override
    public TextView create() {
        return textView;
    }
}
