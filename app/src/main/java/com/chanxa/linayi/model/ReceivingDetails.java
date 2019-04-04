package com.chanxa.linayi.model;


import com.chanxa.linayi.HttpClient.api.ApiResponse;

public class ReceivingDetails extends ApiResponse<ReceivingDetails> {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String actualQuantity;
        private String buyUserName;
        private String communityId;
        private String createTime;
        private String goodsSkuId;
        private String goodsSkuName;
        private String image;
        private String ordersGoodsId;
        private String ordersId;
        private String price;
        private String procureQuantity;
        private String procureStatus;
        private String procurementTaskId;
        private String quantity;
        private String receiveStatus;
        private String serviceFeeString;
        private String supermarketId;
        private String supermarketName;
        private String totalPrice;
        private String userId;

        public String getActualQuantity() {
            return actualQuantity;
        }

        public void setActualQuantity(String actualQuantity) {
            this.actualQuantity = actualQuantity;
        }

        public String getBuyUserName() {
            return buyUserName;
        }

        public void setBuyUserName(String buyUserName) {
            this.buyUserName = buyUserName;
        }

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getGoodsSkuId() {
            return goodsSkuId;
        }

        public void setGoodsSkuId(String goodsSkuId) {
            this.goodsSkuId = goodsSkuId;
        }

        public String getGoodsSkuName() {
            return goodsSkuName;
        }

        public void setGoodsSkuName(String goodsSkuName) {
            this.goodsSkuName = goodsSkuName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getOrdersGoodsId() {
            return ordersGoodsId;
        }

        public void setOrdersGoodsId(String ordersGoodsId) {
            this.ordersGoodsId = ordersGoodsId;
        }

        public String getOrdersId() {
            return ordersId;
        }

        public void setOrdersId(String ordersId) {
            this.ordersId = ordersId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProcureQuantity() {
            return procureQuantity;
        }

        public void setProcureQuantity(String procureQuantity) {
            this.procureQuantity = procureQuantity;
        }

        public String getProcureStatus() {
            return procureStatus;
        }

        public void setProcureStatus(String procureStatus) {
            this.procureStatus = procureStatus;
        }

        public String getProcurementTaskId() {
            return procurementTaskId;
        }

        public void setProcurementTaskId(String procurementTaskId) {
            this.procurementTaskId = procurementTaskId;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getReceiveStatus() {
            return receiveStatus;
        }

        public void setReceiveStatus(String receiveStatus) {
            this.receiveStatus = receiveStatus;
        }

        public String getServiceFeeString() {
            return serviceFeeString;
        }

        public void setServiceFeeString(String serviceFeeString) {
            this.serviceFeeString = serviceFeeString;
        }

        public String getSupermarketId() {
            return supermarketId;
        }

        public void setSupermarketId(String supermarketId) {
            this.supermarketId = supermarketId;
        }

        public String getSupermarketName() {
            return supermarketName;
        }

        public void setSupermarketName(String supermarketName) {
            this.supermarketName = supermarketName;
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
    }
}
