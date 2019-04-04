package com.chanxa.linayi.model;


import com.chanxa.linayi.HttpClient.api.ApiResponse;

import java.util.List;

public class GoodsBinningList extends ApiResponse<GoodsBinningList> {
    
    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        private String currentPage;
        private String totalPage;
        private List<DataBean> data;

        public String getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(String currentPage) {
            this.currentPage = currentPage;
        }

        public String getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(String totalPage) {
            this.totalPage = totalPage;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            private String actualArriveTime;
            private String addressOne;
            private String addressThree;
            private String addressTwo;
            private String amount;
            private String arriveTime;
            private String communityId;
            private String communityStatus;
            private String createTime;
            private String extraFee;
            private String mobile;
            private String ordersId;
            private String quantity;
            private String receiverName;
            private String remark;
            private String saveAmount;
            private String serviceFee;
            private String userId;
            private String userStatus;

            public String getActualArriveTime() {
                return actualArriveTime;
            }

            public void setActualArriveTime(String actualArriveTime) {
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

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getArriveTime() {
                return arriveTime;
            }

            public void setArriveTime(String arriveTime) {
                this.arriveTime = arriveTime;
            }

            public String getCommunityId() {
                return communityId;
            }

            public void setCommunityId(String communityId) {
                this.communityId = communityId;
            }

            public String getCommunityStatus() {
                return communityStatus;
            }

            public void setCommunityStatus(String communityStatus) {
                this.communityStatus = communityStatus;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getExtraFee() {
                return extraFee;
            }

            public void setExtraFee(String extraFee) {
                this.extraFee = extraFee;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getOrdersId() {
                return ordersId;
            }

            public void setOrdersId(String ordersId) {
                this.ordersId = ordersId;
            }

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
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

            public String getSaveAmount() {
                return saveAmount;
            }

            public void setSaveAmount(String saveAmount) {
                this.saveAmount = saveAmount;
            }

            public String getServiceFee() {
                return serviceFee;
            }

            public void setServiceFee(String serviceFee) {
                this.serviceFee = serviceFee;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
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
