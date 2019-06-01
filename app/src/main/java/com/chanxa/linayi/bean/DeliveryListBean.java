package com.chanxa.linayi.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/4/1.
 */

public class DeliveryListBean {

    private DataBeanX data;
    private String respCode;
    private String errorMsg;


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public static class DataBeanX {

        private int totalPage;
        private List<DataBean> data;

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {

            private String boxNo;
            private long boxTime;
            private int ordersId;
            private int quantity;
            private String receiverName;

            public String getBoxNo() {
                return boxNo;
            }

            public void setBoxNo(String boxNo) {
                this.boxNo = boxNo;
            }

            public long getBoxTime() {
                return boxTime;
            }

            public void setBoxTime(long boxTime) {
                this.boxTime = boxTime;
            }

            public int getOrdersId() {
                return ordersId;
            }

            public void setOrdersId(int ordersId) {
                this.ordersId = ordersId;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getReceiverName() {
                return receiverName;
            }

            public void setReceiverName(String receiverName) {
                this.receiverName = receiverName;
            }
        }
    }
}
