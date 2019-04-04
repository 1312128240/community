package com.chanxa.linayi.model;

import java.io.Serializable;

public class BaseResponse implements Serializable {

    //序列化UID 用于反序列化
    private static final long serialVersionUID = 234513596098152098L;

    //0为请求成功 其他错误
    private int errorCode;
    //错误信息
    private String errorMsg;
    private int currentPage;
    private int procurementTaskId;
    private int pageSize;
    private String boxNo;
    private int ordersId;
    private String communityStatus;
    private String procureStatus;


    public String getProcureStatus() {
        return procureStatus;
    }

    public void setProcureStatus(String procureStatus) {
        this.procureStatus = procureStatus;
    }


    public String getCommunityStatus() {
        return communityStatus;
    }

    public void setCommunityStatus(String communityStatus) {
        this.communityStatus = communityStatus;
    }


    public String getBoxNo() {
        return boxNo;
    }

    public void setBoxNo(String boxNo) {
        this.boxNo = boxNo;
    }

    public int getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(int ordersId) {
        this.ordersId = ordersId;
    }



    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getProcurementTaskId() {
        return procurementTaskId;
    }

    public void setProcurementTaskId(int procurementTaskId) {
        this.procurementTaskId = procurementTaskId;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
