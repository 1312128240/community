package com.chanxa.linayi.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/3/30.
 */

public class BinningDetailsBean {

    private String respCode;
    private List<DataBean> data;
    private String errorMsg;

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private int actualQuantity;
        private String boxStatus;
        private int communityId;
        private long createTime;
        private String goodsImage;
        private int goodsSkuId;
        private String goodsSkuName;
        private long orderCreateTime;
        private int ordersId;
        private int price;
        private int procureQuantity;
        private String procureStatus;
        private int procurementTaskId;
        private int quantity;
        private int receiveQuantity;
        private String receiveStatus;
        private String receiverAddress;
        private String receiverName;
        private int supermarketId;
        private long updateTime;
        private int userId;

        public int getActualQuantity() {
            return actualQuantity;
        }

        public void setActualQuantity(int actualQuantity) {
            this.actualQuantity = actualQuantity;
        }

        public String getBoxStatus() {
            return boxStatus;
        }

        public void setBoxStatus(String boxStatus) {
            this.boxStatus = boxStatus;
        }

        public int getCommunityId() {
            return communityId;
        }

        public void setCommunityId(int communityId) {
            this.communityId = communityId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getGoodsImage() {
            return goodsImage;
        }

        public void setGoodsImage(String goodsImage) {
            this.goodsImage = goodsImage;
        }

        public int getGoodsSkuId() {
            return goodsSkuId;
        }

        public void setGoodsSkuId(int goodsSkuId) {
            this.goodsSkuId = goodsSkuId;
        }

        public String getGoodsSkuName() {
            return goodsSkuName;
        }

        public void setGoodsSkuName(String goodsSkuName) {
            this.goodsSkuName = goodsSkuName;
        }

        public long getOrderCreateTime() {
            return orderCreateTime;
        }

        public void setOrderCreateTime(long orderCreateTime) {
            this.orderCreateTime = orderCreateTime;
        }

        public int getOrdersId() {
            return ordersId;
        }

        public void setOrdersId(int ordersId) {
            this.ordersId = ordersId;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getProcureQuantity() {
            return procureQuantity;
        }

        public void setProcureQuantity(int procureQuantity) {
            this.procureQuantity = procureQuantity;
        }

        public String getProcureStatus() {
            return procureStatus;
        }

        public void setProcureStatus(String procureStatus) {
            this.procureStatus = procureStatus;
        }

        public int getProcurementTaskId() {
            return procurementTaskId;
        }

        public void setProcurementTaskId(int procurementTaskId) {
            this.procurementTaskId = procurementTaskId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getReceiveQuantity() {
            return receiveQuantity;
        }

        public void setReceiveQuantity(int receiveQuantity) {
            this.receiveQuantity = receiveQuantity;
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

        public int getSupermarketId() {
            return supermarketId;
        }

        public void setSupermarketId(int supermarketId) {
            this.supermarketId = supermarketId;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
