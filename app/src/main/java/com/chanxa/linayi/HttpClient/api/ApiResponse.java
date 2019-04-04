package com.chanxa.linayi.HttpClient.api;

//import com.orm.SugarRecord;

import java.util.List;

public class ApiResponse<T>{
    /*异地登录*/
    public static final int ERROR_CODE_INVALID = 1004;
    /**token失效*/
    public static final int ERROR_CODE_TOKEN =1005;
    public static final int REQUEST_OK = 1;

    private int rspCode;  // 返回码，1为成功
    private int errCode; //返回错误码
    private String errMsg; //返回错误信息
    private List<T> rows;
    private String request;

    //简化版
    private String respCode;// 返回码，S:成功  F：失败
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess2() {
        return respCode.equals("S");
    }

    public String getRespCode() {
        return respCode;
    }

    public ApiResponse(){

    }

    // 构造函数，初始化code和msg
    public ApiResponse(int rsp_code, String msg) {
        this.rspCode = rsp_code;
        this.errMsg = msg;
    }

    // 判断结果是否成功
    public boolean isSuccess() {
        return rspCode == REQUEST_OK;
    }

    /**
     * 是否失效
     *
     * @return true
     */
    public boolean isInvalid() {
        return errCode == ERROR_CODE_INVALID;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }
}