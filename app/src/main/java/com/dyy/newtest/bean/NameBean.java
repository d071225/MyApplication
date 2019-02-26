package com.dyy.newtest.bean;

/**
 * Created by daiyangyang on 2019/2/16.
 */

public class NameBean {
    private String name;
    private boolean isSelected;

    public NameBean(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
