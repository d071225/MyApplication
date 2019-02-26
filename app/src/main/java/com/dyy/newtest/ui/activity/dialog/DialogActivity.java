package com.dyy.newtest.ui.activity.dialog;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.adapter.NameRvAdapter;
import com.dyy.newtest.bean.NameBean;
import com.dyy.newtest.utils.AppShortCutUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DialogActivity extends AppCompatActivity {

    @BindView(R.id.btn_single_dialog)
    Button btnSingleDialog;
    private AlertDialog alertDialog2;
    private String[] nameList;
    private String selectedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
//        showSingleAlertDialog();
        btnSingleDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showSingleDialog();
//                AppShortCutUtil.addNumShortCut(DialogActivity.this,DialogActivity.class,true,"1",false);
                CopyToClipboard(DialogActivity.this,"homedo");
            }
        });
    }
    public void CopyToClipboard(Context context,String text){
        ToastUtils.showLong("复制成功:"+text);
        ClipboardManager clip = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);
        //clip.getText(); // 粘贴
//        clip.setText(text); // 复制
        clip.setPrimaryClip(ClipData.newPlainText(null,text));
        if (clip.hasPrimaryClip()){
            clip.getPrimaryClip().getItemAt(0).getText();
        }
        getWechatApi();
    }
    private void getWechatApi(){
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // TODO: handle exception
            ToastUtils.showLong("检查到您手机没有安装微信，请安装后使用该功能");
        }
    }
    public void showSingleDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_single,null);
        RecyclerView nameRv = (RecyclerView) view.findViewById(R.id.name_rv);
        Button btnConfirm = (Button) view.findViewById(R.id.btn_confirm);
        nameList = new String[]{"河姆渡123", "河姆渡1234", "河姆渡12345"};
        selectedName=nameList[1];
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showLong("选择了名字："+selectedName);
            }
        });
        NameRvAdapter adapter=new NameRvAdapter(this, nameList, 1, new NameRvAdapter.SingleAlertDialogInterface() {
            @Override
            public void click(int postion) {
                selectedName = nameList[postion];
            }
        });
        nameRv.setAdapter(adapter);
        LinearSnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(nameRv);
        LinearLayoutManager mListManager = new LinearLayoutManager(this);
        nameRv.setLayoutManager(mListManager);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void showSingleAlertDialog() {
        final String[] items = {"单选1", "单选2", "单选3", "单选4"};
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("这是单选框");
        alertBuilder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(DialogActivity.this, items[i], Toast.LENGTH_SHORT).show();
            }
        });

        alertBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog2.dismiss();
            }
        });

        alertBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog2.dismiss();
            }
        });

        alertDialog2 = alertBuilder.create();
        alertDialog2.show();
    }
}
