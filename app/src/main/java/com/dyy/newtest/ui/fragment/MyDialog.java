package com.dyy.newtest.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.dyy.newtest.R;

/**
 * Created by DY on 2018/2/2.
 */

public class MyDialog extends DialogFragment {
    private Activity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity=getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity, R.style.FullscreenTheme);
        builder.setTitle("联系客服")
                .setMessage("10080")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(activity,"去打电话",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消",null);
        return builder.create();
    }
}
