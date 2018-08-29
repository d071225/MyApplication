package com.dyy.newtest.ui.activity.appcache;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SPActivity extends AppCompatActivity {

    @BindView(R.id.et_key)
    EditText etKey;
    @BindView(R.id.et_value)
    EditText etValue;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_remove)
    Button btnRemove;
    @BindView(R.id.btn_clear)
    Button btnClear;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp);
        ButterKnife.bind(this);
        editor = getSharedPreferences("sp_test",MODE_PRIVATE).edit();
        LogUtils.e(android.os.Build.BRAND);
    }

    @OnClick({R.id.btn_save, R.id.btn_remove, R.id.btn_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                editor.putString(etKey.getText().toString(),etValue.getText().toString()).commit();
                break;
            case R.id.btn_remove:
                editor.remove(etKey.getText().toString()).commit();
                break;
            case R.id.btn_clear:
                editor.clear().commit();
                break;
        }
    }
}
