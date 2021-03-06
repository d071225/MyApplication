package com.dyy.newtest;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by DY on 2018/2/1.
 */

public class Book implements Parcelable{
    private int bookId;
    private String name;
    protected Book(Parcel in) {
        this.bookId=in.readInt();
        this.name=in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(name);
    }
    /**
     * 参数是一个Parcel,用它来存储与传输数据
     * @param dest
     */
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        bookId = dest.readInt();
        name = dest.readString();
    }
}
