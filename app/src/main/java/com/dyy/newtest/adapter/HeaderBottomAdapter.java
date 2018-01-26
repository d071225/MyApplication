package com.dyy.newtest.adapter;

/**
 * Created by DY on 2018/1/9.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dyy.newtest.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junguang_Gao on 2016/11/24.
 */
public class HeaderBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    private final int width;
    //item的高度
    private List<Integer> mHeights;

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<String> headerData;
    private List<String> headerTitles;
    private List<String> itemData;
    private List<Integer> heightList;
    private int mHeaderCount=1;//头部View个数
    private int mBottomCount=1;//底部View个数

    public HeaderBottomAdapter(Context context, List<String> headerData,List<String> headerTitles, List<String> itemData) {
        mContext = context;
        this.headerData=headerData;
        this.headerTitles=headerTitles;
        this.itemData=itemData;
        mLayoutInflater = LayoutInflater.from(context);
        //屏幕的宽度(px值）
        int screenWidth = context.getResources().getDisplayMetrics().widthPixels;;
        //Item的宽度，或图片的宽度
        width = screenWidth/4;

        heightList = new ArrayList<>();
        Log.e("height","初始化++++++++++++headerData"+headerData.size());
        for (int i = 0; i < headerData.size() ; i++) {
            int height = (int) (Math.random()*400 + 400);
            Log.e("height","初始化++++++++++++"+height);
            heightList.add(height);
        }
    }
    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }
    //内容长度
    public int getContentItemCount(){
        return itemData.size();
    }
    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }
    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        Log.e("getItemViewType","getItemViewType:"+position);
        if (isHeaderView(position)) {
            //头部View
            Log.e("getItemViewType","头部View:"+position);
            return ITEM_TYPE_HEADER;
        } else if (isBottomView(position)) {
            //底部View
            Log.e("getItemViewType","底部View:"+position);
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            Log.e("getItemViewType","内容View:"+position);
            return ITEM_TYPE_CONTENT;
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType ==ITEM_TYPE_HEADER) {
            Log.e("onCreateViewHolder","onCreateViewHolder ---头部View:"+viewType);
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.rv_header, parent, false));
        } else if (viewType == ITEM_TYPE_CONTENT) {
            Log.e("onCreateViewHolder","onCreateViewHolder ---内容View:"+viewType);
            return new ContentViewHolder(mLayoutInflater.inflate(R.layout.pic_item, parent, false));
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            Log.e("onCreateViewHolder","onCreateViewHolder ---底部View:"+viewType);
            return new BottomViewHolder(mLayoutInflater.inflate(R.layout.rv_footer, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof HeaderViewHolder) {
            // 获取cardview的布局属性，记住这里要是布局的最外层的控件的布局属性，如果是里层的会报cast错误
            StaggeredGridLayoutManager.LayoutParams slp = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            // 最最关键一步，设置当前view占满列数，这样就可以占据两列实现头部了
            slp.setFullSpan(true);

            Banner banner = ((HeaderViewHolder) holder).banner;
            //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
            //可选样式如下:
            //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
            //2. Banner.NUM_INDICATOR   显示数字指示器
            //3. Banner.NUM_INDICATOR_TITLE 显示数字指示器和标题
            //4. Banner.CIRCLE_INDICATOR_TITLE  显示圆形指示器和标题
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);

            //设置轮播样式（没有标题默认为右边,有标题时默认左边）
            //可选样式:
            //Banner.LEFT   指示器居左
            //Banner.CENTER 指示器居中
            //Banner.RIGHT  指示器居右
            banner.setIndicatorGravity(BannerConfig.RIGHT);

            //设置轮播要显示的标题和图片对应（如果不传默认不显示标题）
            banner.setBannerTitles(headerTitles);

            //设置是否自动轮播（不设置则默认自动）
            banner.isAutoPlay(true) ;

            //设置轮播图片间隔时间（不设置默认为2000）
            banner.setDelayTime(5000);
            //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
            //所有设置参数方法都放在此方法之前执行
            //banner.setImages(images);

            //自定义图片加载框架
            banner.setImages(headerData);
            banner.setBannerAnimation(Transformer.BackgroundToForeground);
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).asBitmap().centerCrop().into(imageView);
                }
            });
            //设置点击事件，下标是从1开始
            banner.setOnBannerClickListener(new OnBannerClickListener() {//设置点击事件
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(mContext, "你点击了：" + position, Toast.LENGTH_LONG).show();
                }
            });
            banner.start();
        } else if (holder instanceof ContentViewHolder) {
//            ((ContentViewHolder) holder).textView.setText(datas[position - mHeaderCount]);
            final ImageView iv = ((ContentViewHolder) holder).imageView;
            ViewGroup.LayoutParams lp = iv.getLayoutParams();
            lp.height=heightList.get(position-mHeaderCount);
            lp.width=width;
            iv.setLayoutParams(lp);
            Glide.with(mContext).load(itemData.get(position-mHeaderCount)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round)
                   .into(iv);
        } else if (holder instanceof BottomViewHolder) {
            // 获取cardview的布局属性，记住这里要是布局的最外层的控件的布局属性，如果是里层的会报cast错误
            StaggeredGridLayoutManager.LayoutParams slp = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            // 最最关键一步，设置当前view占满列数，这样就可以占据两列实现头部了
            slp.setFullSpan(true);
        }

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null)
        {
            //itemView:ViewHolder的一个item
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos-1);

                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemLongClick(holder.itemView, pos-1);
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        Log.e("onCreateViewHolder","getItemCount *** getContentItemCount():"+getContentItemCount());
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

    //内容 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ContentViewHolder(View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.iv_pic);
        }
    }
    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private Banner banner;
        public HeaderViewHolder(View itemView) {
            super(itemView);
            banner= (Banner) itemView.findViewById(R.id.banner_pic);
        }
    }
    //底部 ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivFooter;
        public BottomViewHolder(View itemView) {
            super(itemView);
            ivFooter=(ImageView)itemView.findViewById(R.id.iv_foot);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}
