package com.dyy.newtest.test;

import com.dyy.newtest.bean.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by DY on 2018/2/24.
 */

public class SerializableDemo {
    public static void main(String[] args) {
        User user=new User();
        user.setAge(15);
        user.setName("zhangsan");
        user.setPassword("123456");
        try {
            //序列化保存对象
            ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("test.txt"));
            oos.writeObject(user);
            oos.close();
            //反序列化读取对象
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream("test.txt"));
            User user1 = (User) ois.readObject();
            System.out.println(user1.getName()+";年龄"+user1.getAge()+";密码"+user1.getPassword());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
