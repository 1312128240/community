
package com.chanxa.linayi.bean;

import java.util.ArrayList;


public class Country {

    /**标识是否选中*/
    private boolean isChoose;
    /**国家名称*/
    private String name;
    /**国家编码*/
    private String code;
    /**上一级编码*/
    private String parent;
    /**省列表*/
    private ArrayList<Province> provinceArrayList;

    public ArrayList<Province> getProvinceArrayList() {
        return provinceArrayList;
    }

    public void setProvinceArrayList(ArrayList<Province> provinceArrayList) {
        this.provinceArrayList = provinceArrayList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setIsChoose(boolean isChoose) {
        this.isChoose = isChoose;
    }
}
