package com.dyy.newtest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dyy.newtest.R;

import java.util.List;


public class ConstellationAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int checkItemPosition = -1;
    private boolean isChecked=false;

    public void setCheckItem(int position) {
        if (checkItemPosition!=position){
            isChecked=true;
            checkItemPosition = position;
            notifyDataSetChanged();
        }else {
            if (!isChecked) {
                checkItemPosition = position;
                notifyDataSetChanged();
                isChecked = true;
            } else {
                checkItemPosition = position;
                notifyDataSetChanged();
                isChecked = false;
            }
        }
    }
    public boolean getCheckedState(){
        return isChecked;
    }
    public ConstellationAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_constellation_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mText.setText(list.get(position));
//        if (checkItemPosition != -1) {
            if (checkItemPosition == position&&isChecked) {
                viewHolder.mText.setTextColor(Color.BLUE);
                viewHolder.mText.setBackgroundResource(R.drawable.check_bg);
            } else {
                viewHolder.mText.setTextColor(Color.BLACK);
                viewHolder.mText.setBackgroundResource(R.drawable.uncheck_bg);
//            }
        }
    }

    static class ViewHolder {
//        @BindView(R.id.text)
        TextView mText;

        ViewHolder(View view) {
//            ButterKnife.bind(view);
            mText= (TextView) view.findViewById(R.id.text);
        }
    }
}
