package com.dyy.newtest.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntRange;
import android.support.annotation.Size;

/**
 * Created by DY on 2018/1/3.
 */

public class UserParcelable implements Parcelable {
    private String name;
    private int age;
    private String password;

    public UserParcelable() {
    }

    protected UserParcelable(Parcel in) {
        name = in.readString();
        age = in.readInt();
        password = in.readString();
    }

    public static final Creator<UserParcelable> CREATOR = new Creator<UserParcelable>() {
        @Override
        public UserParcelable createFromParcel(Parcel in) {
            return new UserParcelable(in);
        }

        @Override
        public UserParcelable[] newArray(int size) {
            return new UserParcelable[size];
        }
    };

    public String getName() {
        return name;
    }
    public void setName(@Size(min = 3) String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(@IntRange(from=0,to=150) int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@Size(max = 20) String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
        dest.writeString(password);
    }
}
