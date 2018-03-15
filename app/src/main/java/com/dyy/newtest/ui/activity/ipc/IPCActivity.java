package com.dyy.newtest.ui.activity.ipc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.dyy.newtest.R;
import com.dyy.newtest.bean.User;
import com.dyy.newtest.bean.UserParcelable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IPCActivity extends AppCompatActivity {

    @BindView(R.id.btn_fork2)
    Button btnFork2;
    @BindView(R.id.btn_fork3)
    Button btnFork3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipc);
        ButterKnife.bind(this);
        User.count=2;
        LogUtils.a("User.count==="+User.count);
    }

    @OnClick({R.id.btn_fork2, R.id.btn_fork3})
    public void onClick(View view) {
        String fileName=getExternalCacheDir()+ File.separator+"test.txt";
        String fileName2=getExternalCacheDir()+ File.separator+"test2.txt";
        switch (view.getId()) {
            case R.id.btn_fork2:
                User user=new User();
                user.setAge(15);
                user.setName("zhangsan");
                user.setPassword("123456");
                UserParcelable user2=new UserParcelable();
                user2.setAge(16);
                user2.setName("lisi");
                user2.setPassword("abc");
                try {
                    //序列化保存对象

                    ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(fileName));
                    oos.writeObject(user);
                    oos.close();
                    //反序列化读取对象
//                    ObjectInputStream ois=new ObjectInputStream(new FileInputStream(fileName));
//                    User user1 = (User) ois.readObject();
//                    System.out.println(user1.getName()+";年龄"+user1.getAge()+";密码"+user1.getPassword());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Intent intent=new Intent(this,SecondActivity.class);
                intent.putExtra("par",user2);
                startActivity(intent);
//                startActivity(new Intent(this,SecondActivity.class));
                break;
            case R.id.btn_fork3:
                try {
                    //序列化保存对象
//                    String fileName=getExternalCacheDir()+ File.separator+"test.txt";
//                    ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(fileName));
//                    oos.writeObject(user);
//                    oos.close();
                    //反序列化读取对象
                    ObjectInputStream ois=new ObjectInputStream(new FileInputStream(fileName2));
//                    User user1 = (User) ois.readObject();
                    UserParcelable user1 = (UserParcelable) ois.readObject();
                    System.out.println(user1.getName()+";年龄"+user1.getAge()+";密码"+user1.getPassword());
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
//                startActivity(new Intent(this,ThirdActivity.class));
                break;
        }
    }
}
