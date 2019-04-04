package com.chanxa.linayi.model;


import com.chanxa.linayi.HttpClient.api.ApiResponse;

import java.util.List;

public class GoodsBinningDetails extends ApiResponse<GoodsBinningDetails> {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String actualQuantity;
        private String communityId;
        private String createTime;
        private String goodsImage;
        private String goodsSkuId;
        private String goodsSkuName;
        private String orderCreateTime;
        private String ordersId;
        private String price;
        private String procureStatus;
        private String procurementTaskId;
        private String quantity;
        private String receiveStatus;
        private String receiverAddress;
        private String receiverName;
        private String supermarketId;
        private String userId;

        public String getActualQuantity() {
            return actualQuantity;
        }

        public void setActualQuantity(String actualQuantity) {
            this.actualQuantity = actualQuantity;
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

        public String getGoodsImage() {
            return goodsImage;
        }

        public void setGoodsImage(String goodsImage) {
            this.goodsImage = goodsImage;
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

        public String getOrderCreateTime() {
            return orderCreateTime;
        }

        public void setOrderCreateTime(String orderCreateTime) {
            this.orderCreateTime = orderCreateTime;
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

        public String getReceiverAddress() {
            return receiverAddress;
        }

        public void setReceiverAddress(String receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        public String getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(String receiverName) {
            this.receiverName = receiverName;
        }

        public String getSupermarketId() {
            return supermarketId;
        }

        public void setSupermarketId(String supermarketId) {
            this.supermarketId = supermarketId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
