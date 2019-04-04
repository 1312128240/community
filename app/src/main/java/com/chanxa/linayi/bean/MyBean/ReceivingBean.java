package com.chanxa.linayi.bean.MyBean;

import java.util.List;

/**
 * Created by Administrator on 2019/3/29.
 */

public class ReceivingBean {

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

            private int actualQuantity;
            private String buyUserName;
            private int communityId;
            private long createTime;
            private int goodsSkuId;
            private int ordersGoodsId;
            private int ordersId;
            private int price;
            private int procureQuantity;
            private String procureStatus;
            private int procurementTaskId;
            private int quantity;
            private String receiveStatus;
            private int supermarketId;
            private String supermarketName;
            private int userId;

            public int getActualQuantity() {
                return actualQuantity;
            }

            public void setActualQuantity(int actualQuantity) {
                this.actualQuantity = actualQuantity;
            }

            public String getBuyUserName() {
                return buyUserName;
            }

            public void setBuyUserName(String buyUserName) {
                this.buyUserName = buyUserName;
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

            public int getGoodsSkuId() {
                return goodsSkuId;
            }

            public void setGoodsSkuId(int goodsSkuId) {
                this.goodsSkuId = goodsSkuId;
            }

            public int getOrdersGoodsId() {
                return ordersGoodsId;
            }

            public void setOrdersGoodsId(int ordersGoodsId) {
                this.ordersGoodsId = ordersGoodsId;
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

            public String getReceiveStatus() {
                return receiveStatus;
            }

            public void setReceiveStatus(String receiveStatus) {
                this.receiveStatus = receiveStatus;
            }

            public int getSupermarketId() {
                return supermarketId;
            }

            public void setSupermarketId(int supermarketId) {
                this.supermarketId = supermarketId;
            }

            public String getSupermarketName() {
                return supermarketName;
            }

            public void setSupermarketName(String supermarketName) {
                this.supermarketName = supermarketName;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }
        }
    }
}
