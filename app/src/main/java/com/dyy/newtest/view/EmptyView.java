package com.dyy.newtest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dyy.newtest.R;


/**
 * Created by DY on 2018/3/7.
 */

public class EmptyView extends LinearLayout implements View.OnClickListener {
    public final static int NETWORK_LOADING=1;
    public final static int NETWORK_ERROR=2;
    public final static int NODATA=3;
    private String loadingContent;
    private String noDataContent;
    private String errorContent;

    private Bitmap noDataImg;
    private int errorImg=-1;
    private ImageView imgError;
    private TextView tvError;
    private ProgressBar pbLoading;

    private Context mContext;
    public EmptyView(Context context) {
        this(context,null);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        View view = LayoutInflater.from(context).inflate(R.layout.empty_view, this);
        imgError = (ImageView) view.findViewById(R.id.img_error);
        tvError = (TextView) view.findViewById(R.id.tv_error);
        pbLoading = (ProgressBar) view.findViewById(R.id.pb_loading);
        setOnClickListener(this);
    }

    public void setLoadingContent(String loadingContent) {
        this.loadingContent = loadingContent;
    }

    public void setNoDataContent(String noDataContent) {
        this.noDataContent = noDataContent;
    }

    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent;
    }

    public void setNoDataImg(Bitmap noDataImg) {
        this.noDataImg = noDataImg;
    }

    public void setErrorImg(int errorImg) {
        this.errorImg = errorImg;
    }

    public void setType(int type){
        switch (type){
            case NETWORK_LOADING:
                imgError.setVisibility(GONE);
                tvError.setVisibility(VISIBLE);
                pbLoading.setVisibility(VISIBLE);
                tvError.setText(loadingContent==null?"正在加载...":loadingContent);
                break;
            case NETWORK_ERROR:
                imgError.setVisibility(VISIBLE);
                tvError.setVisibility(VISIBLE);
                pbLoading.setVisibility(GONE);
                imgError.setImageResource(errorImg==-1?R.mipmap.ic_launcher:errorImg);
                tvError.setText(loadingContent==null?"点击屏幕,重新加载":loadingContent);
                break;
            case NODATA:
                imgError.setVisibility(VISIBLE);
                tvError.setVisibility(VISIBLE);
                pbLoading.setVisibility(GONE);
                imgError.setImageBitmap(noDataImg==null? BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_launcher):noDataImg);
                tvError.setText(loadingContent==null?"点击屏幕,重新加载":loadingContent);
                break;
        }
        setVisibility(VISIBLE);
    }

    @Override
    public void onClick(View v) {
        onClickListener.onClick(v);
    }
    public OnClickListener onClickListener;
    public void setEmptyOnClickListener(OnClickListener onClickListener){
        this.onClickListener=onClickListener;
    }
}
