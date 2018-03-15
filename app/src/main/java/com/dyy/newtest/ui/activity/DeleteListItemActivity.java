package com.dyy.newtest.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.view.DeleteListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeleteListItemActivity extends AppCompatActivity {

    @BindView(R.id.delete_lv)
    DeleteListView deleteLv;
    private List<String> mStringList;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_list_item);
        ButterKnife.bind(this);
        mStringList=new ArrayList<>();
        initData();
        myAdapter = new MyAdapter();
        deleteLv.setAdapter(myAdapter);
        deleteLv.setOnDeleteItemListener(new DeleteListView.OnDeleteItemListener() {
            @Override
            public void doDeleteItem(int position) {
                mStringList.remove(position);
                myAdapter.notifyDataSetChanged();
            }
        });
//        deleteLv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.showLong("listview点击成功");
//            }
//        });
    }
    public class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mStringList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null) {
                convertView = LayoutInflater.from(DeleteListItemActivity.this).inflate(R.layout.listview_item, parent, false);
            }
            TextView tvContent= (TextView) convertView.findViewById(R.id.tv_item_content);
            tvContent.setText(mStringList.get(position));
            tvContent.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    LogUtils.e("TextView setOnTouchListener onTouch");
                    return true;
                }
            });
            tvContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.e("TextView setOnClickListener onClick");
                    ToastUtils.showLong("点击成功");
                }
            });
            return convertView;
        }
    }
    public void initData(){
        for (int i=0;i<20;i++){
            mStringList.add("item "+i);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.e("activity onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        deleteLv.hiddenDeleteBtn();
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        LogUtils.e("activity dispatchTouchEvent");
//        return false;
//    }

}
