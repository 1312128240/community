package com.chanxa.linayi.bean;

import java.util.List;

public class TaskDeliveryBean {

    private DataBeanX data;
    private String respCode;
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
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


        private int currentPage;
        private int totalPage;
        private List<DataBean> data;

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

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

            private long actualArriveTime;
            private String addressOne;
            private String addressThree;
            private String addressTwo;
            private int amount;
            private long arriveTime;
            private int communityId;
            private String communityStatus;
            private long createTime;
            private int delivererId;
            private String delivererName;
            private String mobile;
            private int ordersId;
            private int quantity;
            private String receiverName;
            private String remark;
            private int saveAmount;
            private int serviceFee;
            private int userId;
            private String userStatus;
            private String buyUserName;



            public String getBuyUserName() {
                return buyUserName;
            }

            public void setBuyUserName(String buyUserName) {
                this.buyUserName = buyUserName;
            }

            public long getActualArriveTime() {
                return actualArriveTime;
            }

            public void setActualArriveTime(long actualArriveTime) {
                this.actualArriveTime = actualArriveTime;
            }

            public String getAddressOne() {
                return addressOne;
            }

            public void setAddressOne(String addressOne) {
                this.addressOne = addressOne;
            }

            public String getAddressThree() {
                return addressThree;
            }

            public void setAddressThree(String addressThree) {
                this.addressThree = addressThree;
            }

            public String getAddressTwo() {
                return addressTwo;
            }

            public void setAddressTwo(String addressTwo) {
                this.addressTwo = addressTwo;
            }

            public int getAmount() {
                return amount;
            }

            public void setAmount(int amount) {
                this.amount = amount;
            }

            public long getArriveTime() {
                return arriveTime;
            }

            public void setArriveTime(long arriveTime) {
                this.arriveTime = arriveTime;
            }

            public int getCommunityId() {
                return communityId;
            }

            public void setCommunityId(int communityId) {
                this.communityId = communityId;
            }

            public String getCommunityStatus() {
                return communityStatus;
            }

            public void setCommunityStatus(String communityStatus) {
                this.communityStatus = communityStatus;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public int getDelivererId() {
                return delivererId;
            }

            public void setDelivererId(int delivererId) {
                this.delivererId = delivererId;
            }

            public String getDelivererName() {
                return delivererName;
            }

            public void setDelivererName(String delivererName) {
                this.delivererName = delivererName;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
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

            public String getRemark() {
                return remark;
            }

            public void setRemark(String remark) {
                this.remark = remark;
            }

            public int getSaveAmount() {
                return saveAmount;
            }

            public void setSaveAmount(int saveAmount) {
                this.saveAmount = saveAmount;
            }

            public int getServiceFee() {
                return serviceFee;
            }

            public void setServiceFee(int serviceFee) {
                this.serviceFee = serviceFee;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUserStatus() {
                return userStatus;
            }

            public void setUserStatus(String userStatus) {
                this.userStatus = userStatus;
            }
        }
    }
}
