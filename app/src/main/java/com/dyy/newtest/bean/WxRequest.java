package com.dyy.newtest.bean;

/**
 * Created by DY on 2018/1/4.
 */

public class WxRequest {
    private String app_id;
    private String msg_content;
    private String template_id;
    private String sys_id;
    private String mcht_msg_no;
    private String sign_type;
    private String sign;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getMsg_content() {
        return msg_content;
    }

    public void setMsg_content(String msg_content) {
        this.msg_content = msg_content;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getSys_id() {
        return sys_id;
    }

    public void setSys_id(String sys_id) {
        this.sys_id = sys_id;
    }

    public String getMcht_msg_no() {
        return mcht_msg_no;
    }

    public void setMcht_msg_no(String mcht_msg_no) {
        this.mcht_msg_no = mcht_msg_no;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
