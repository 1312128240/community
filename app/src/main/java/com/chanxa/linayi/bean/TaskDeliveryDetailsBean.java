package com.chanxa.linayi.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/3/29.
 */

public class TaskDeliveryDetailsBean {

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

        private long actualArriveTime;
        private String address;
        private String addressOne;
        private String addressThree;
        private String addressTwo;
        private int amount;
        private long arriveTime;
        private long boxTime;
        private int communityId;
        private String communityStatus;
        private long createTime;
        private int delivererId;
        private int extraFee;
        private String goodsTotalPrice;
        private String mobile;
        private int ordersId;
        private String payPrice;
        private int quantity;
        private String receiverName;
        private String remark;
        private int saveAmount;
        private int serviceFee;
        private String serviceMobile;
        private String totalPrice;
        private int userId;
        private String userStatus;
        private List<ShoppingCarListBean> shoppingCarList;

        public long getActualArriveTime() {
            return actualArriveTime;
        }

        public void setActualArriveTime(long actualArriveTime) {
            this.actualArriveTime = actualArriveTime;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public long getBoxTime() {
            return boxTime;
        }

        public void setBoxTime(long boxTime) {
            this.boxTime = boxTime;
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

        public int getExtraFee() {
            return extraFee;
        }

        public void setExtraFee(int extraFee) {
            this.extraFee = extraFee;
        }

        public String getGoodsTotalPrice() {
            return goodsTotalPrice;
        }

        public void setGoodsTotalPrice(String goodsTotalPrice) {
            this.goodsTotalPrice = goodsTotalPrice;
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

        public String getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(String payPrice) {
            this.payPrice = payPrice;
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

        public String getServiceMobile() {
            return serviceMobile;
        }

        public void setServiceMobile(String serviceMobile) {
            this.serviceMobile = serviceMobile;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
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

        public List<ShoppingCarListBean> getShoppingCarList() {
            return shoppingCarList;
        }

        public void setShoppingCarList(List<ShoppingCarListBean> shoppingCarList) {
            this.shoppingCarList = shoppingCarList;
        }

        public static class ShoppingCarListBean {

            private String goodsName;
            private String goodsSkuImage;
            private String heJiPrice;
            private String minPrice;
            private String minSupermarketName;
            private int quantity;

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsSkuImage() {
                return goodsSkuImage;
            }

            public void setGoodsSkuImage(String goodsSkuImage) {
                this.goodsSkuImage = goodsSkuImage;
            }

            public String getHeJiPrice() {
                return heJiPrice;
            }

            public void setHeJiPrice(String heJiPrice) {
                this.heJiPrice = heJiPrice;
            }

            public String getMinPrice() {
                return minPrice;
            }

            public void setMinPrice(String minPrice) {
                this.minPrice = minPrice;
            }

            public String getMinSupermarketName() {
                return minSupermarketName;
            }

            public void setMinSupermarketName(String minSupermarketName) {
                this.minSupermarketName = minSupermarketName;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }
        }
    }
}
