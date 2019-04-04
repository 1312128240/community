package com.chanxa.linayi.model;



import com.chanxa.linayi.HttpClient.api.ApiResponse;

import java.util.List;

public class OrderDetails extends ApiResponse<OrderDetails> {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String address;
        private String communityId;
        private String communityName;
        private String communityPhone;
        private String communityStatus;
        private String createDate;
        private String createDateStr;
        private String deliveryTime;
        private String extraFeeString;
        private String goodsTotalPrice;
        private String mobile;
        private String ordersId;
        private String payNumber;
        private String payPrice;
        private String receiptTime;
        private String receiverName;
        private String remark;
        private String serviceFeeString;
        private String serviceMobile;
        private String status;
        private String totalPrice;
        private String userStatus;
        private List<ShoppingCarListBean> shoppingCarList;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public String getCommunityName() {
            return communityName;
        }

        public void setCommunityName(String communityName) {
            this.communityName = communityName;
        }

        public String getCommunityPhone() {
            return communityPhone;
        }

        public void setCommunityPhone(String communityPhone) {
            this.communityPhone = communityPhone;
        }

        public String getCommunityStatus() {
            return communityStatus;
        }

        public void setCommunityStatus(String communityStatus) {
            this.communityStatus = communityStatus;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreateDateStr() {
            return createDateStr;
        }

        public void setCreateDateStr(String createDateStr) {
            this.createDateStr = createDateStr;
        }

        public String getDeliveryTime() {
            return deliveryTime;
        }

        public void setDeliveryTime(String deliveryTime) {
            this.deliveryTime = deliveryTime;
        }

        public String getExtraFeeString() {
            return extraFeeString;
        }

        public void setExtraFeeString(String extraFeeString) {
            this.extraFeeString = extraFeeString;
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

        public String getPayNumber() {
            return payNumber;
        }

        public void setPayNumber(String payNumber) {
            this.payNumber = payNumber;
        }

        public String getPayPrice() {
            return payPrice;
        }

        public void setPayPrice(String payPrice) {
            this.payPrice = payPrice;
        }

        public String getReceiptTime() {
            return receiptTime;
        }

        public void setReceiptTime(String receiptTime) {
            this.receiptTime = receiptTime;
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

        public String getServiceFeeString() {
            return serviceFeeString;
        }

        public void setServiceFeeString(String serviceFeeString) {
            this.serviceFeeString = serviceFeeString;
        }

        public String getServiceMobile() {
            return serviceMobile;
        }

        public void setServiceMobile(String serviceMobile) {
            this.serviceMobile = serviceMobile;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
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
            private String goodsSkuId;
            private String goodsSkuImage;
            private String heJiPrice;
            private String maxPrice;
            private String maxSupermarketName;
            private String minPrice;
            private String minSupermarketName;
            private String quantity;
            private String spreadRate;
            private String status;

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsSkuId() {
                return goodsSkuId;
            }

            public void setGoodsSkuId(String goodsSkuId) {
                this.goodsSkuId = goodsSkuId;
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

            public String getMaxPrice() {
                return maxPrice;
            }

            public void setMaxPrice(String maxPrice) {
                this.maxPrice = maxPrice;
            }

            public String getMaxSupermarketName() {
                return maxSupermarketName;
            }

            public void setMaxSupermarketName(String maxSupermarketName) {
                this.maxSupermarketName = maxSupermarketName;
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

            public String getSpreadRate() {
                return spreadRate;
            }

            public void setSpreadRate(String spreadRate) {
                this.spreadRate = spreadRate;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
