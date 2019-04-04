package com.chanxa.autoupdatelibrary.model;

/**
 * Created by cretin on 2017/3/8.
 */

public class UpdateEntity {

    public int versionCode = 0;
    //是否强制更新 0 不强制更新 1 hasAffectCodes拥有字段强制更新 2 所有版本强制更新
    public int isForceUpdate = 0;
    //版本号 描述作用
    public String versionName = "";
    //新安装包的下载地址
    public String download_url = "";
    //更新日志
    public String update_content = "";
    //安装包大小 单位字节
    public String size = "0";

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(int isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }


    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getUpdate_content() {
        return update_content;
    }

    public void setUpdate_content(String update_content) {
        this.update_content = update_content;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
