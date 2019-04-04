package com.chanxa.linayi.model;



import com.chanxa.linayi.HttpClient.api.ApiResponse;

import java.util.List;

public class DeliveryDetails extends ApiResponse<DeliveryDetails> {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String actualArriveTime;
        private String address;
        private String addressOne;
        private String addressThree;
        private String addressTwo;
        private String amount;
        private String arriveTime;
        private String boxTime;
        private String communityId;
        private String communityStatus;
        private String createTime;
        private String delivererId;
        private String deliveryFinishTime;
        private String extraFee;
        private String goodsTotalPrice;
        private String mobile;
        private String ordersId;
        private String payPrice;
        private String quantity;
        private String receiverName;
        private String remark;
        private String saveAmount;
        private String serviceFee;
        private String serviceMobile;
        private String totalPrice;
        private String userId;
        private String userStatus;
        private List<ShoppingCarListBean> shoppingCarList;

        public String getActualArriveTime() {
            return actualArriveTime;
        }

        public void setActualArriveTime(String actualArriveTime) {
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

        public String getBoxTime() {
            return boxTime;
        }

        public void setBoxTime(String boxTime) {
            this.boxTime = boxTime;
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

        public String getDelivererId() {
            return delivererId;
        }

        public void setDelivererId(String delivererId) {
            this.delivererId = delivererId;
        }

        public String getDeliveryFinishTime() {
            return deliveryFinishTime;
        }

        public void setDeliveryFinishTime(String deliveryFinishTime) {
            this.deliveryFinishTime = deliveryFinishTime;
        }

        public String getExtraFee() {
            return extraFee;
        }

        public void setExtraFee(String extraFee) {
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

        public String getOrdersId() {
            return ordersId;
        }

        public void setOrdersId(String ordersId) {
            this.ordersId = ordersId;
        }

        public String getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(String payPrice) {
            this.payPrice = payPrice;
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
            private String quantity;

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

            public String getQuantity() {
                return quantity;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }
        }
    }
}
