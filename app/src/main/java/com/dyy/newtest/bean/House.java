package com.dyy.newtest.bean;

/**
 * Created by DY on 2018/1/16.
 */

public class House {
    private String name;
    private String des;

    public House(String name, String des) {
        this.name = name;
        this.des = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
