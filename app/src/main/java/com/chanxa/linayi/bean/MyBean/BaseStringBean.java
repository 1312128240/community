package com.chanxa.linayi.bean.MyBean;

/**
 * Created by chanxa on 2017/11/9.
 */

public class BaseStringBean {

    private int position;
    private boolean isLast;
    private String name;
    private String value;
    private String ordersId;
    private String mobile;
    private String password;
    private String respCode;
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }
    public String getRespCode() {
        return respCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
