package com.dyy.newtest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.dyy.newtest.R;
import com.dyy.newtest.bean.NameBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by daiyangyang on 2019/2/16.
 */

public class NameRvAdapter extends RecyclerView.Adapter<NameRvAdapter.NameRvVH>{
    private Context context;
    private final List<NameBean> nameBeanList;
    private SingleAlertDialogInterface singleAlertDialogInterface;
    public NameRvAdapter(Context context,String[] nameList,int selected,SingleAlertDialogInterface singleAlertDialogInterface) {
        this.context=context;
        this.singleAlertDialogInterface=singleAlertDialogInterface;
        nameBeanList = new ArrayList<>();
        for (int i=0;i<nameList.length;i++){
            if (i==selected) {
                nameBeanList.add(new NameBean(nameList[i],true));
            }else {
                nameBeanList.add(new NameBean(nameList[i],false));
            }
        }
    }

    @Override
    public NameRvVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_check_item,parent,false);
        return new NameRvVH(view);
    }

    @Override
    public void onBindViewHolder(NameRvVH holder, final int position) {
        holder.name_tv.setText(nameBeanList.get(position).getName());
        holder.name_rb.setChecked(nameBeanList.get(position).isSelected());
        holder.name_rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectName(position);
            }
        });
        holder.name_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectName(position);
            }
        });
    }

    private void selectName(int position) {
        for (NameBean nameBean : nameBeanList){
            nameBean.setSelected(false);
        }
        nameBeanList.get(position).setSelected(true);
        singleAlertDialogInterface.click(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return nameBeanList==null?0:nameBeanList.size();
    }
    public interface SingleAlertDialogInterface{
        void click(int postion);
    }
    public class NameRvVH extends RecyclerView.ViewHolder{

        private final LinearLayout name_ll;
        private final RadioButton name_rb;
        private final TextView name_tv;

        public NameRvVH(View itemView) {
            super(itemView);
            name_ll = (LinearLayout) itemView.findViewById(R.id.name_ll);
            name_rb = (RadioButton) itemView.findViewById(R.id.name_rb);
            name_tv = (TextView) itemView.findViewById(R.id.name_tv);
        }
    }
}
