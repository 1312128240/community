package com.chanxa.linayi.bean.MyBean;

/**
 * Created by Administrator on 2019/3/28.
 */

public class LoginBean {

    private DataBean data;
    private String respCode;
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }



    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public static class DataBean {

        private String CommunityName;
        private String accessToken;

        public String getCommunityName() {
            return CommunityName;
        }

        public void setCommunityName(String CommunityName) {
            this.CommunityName = CommunityName;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }
    }
}
