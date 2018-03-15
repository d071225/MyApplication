package com.dyy.newtest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dyy.newtest.R;

/**
 * Created by DY on 2018/2/28.
 */

public class TextTitleBar extends RelativeLayout {

    private ImageView back;
    private TextView tvTitle;

    public TextTitleBar(Context context) {
        this(context,null);
    }

    public TextTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TextTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.text_title_bar, this);
        back = (ImageView) view.findViewById(R.id.img_back);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
    }
    public void setTitle(String title){
        tvTitle.setText(title);
    }
    public void setBack(int id){
        back.setImageResource(id);
    }
    public void setBackOnClickListener(OnClickListener onClickListener){
        back.setOnClickListener(onClickListener);
    }
    public void setTitleOnClickListener(OnClickListener onClickListener){
        tvTitle.setOnClickListener(onClickListener);
    }
}
