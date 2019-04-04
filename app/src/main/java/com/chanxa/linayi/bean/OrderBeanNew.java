package com.chanxa.linayi.bean;


import com.chanxa.linayi.HttpClient.api.ApiResponse;

import java.util.List;

public class OrderBeanNew extends ApiResponse<OrderBeanNew> {

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
            private String actualQuantity;
            private String communityId;
            private String createTime;
            private String goodsSkuId;
            private String ordersGoodsId;
            private String ordersId;
            private String price;
            private String procureStatus;
            private String procurementTaskId;
            private String quantity;
            private String receiveStatus;
            private String supermarketId;
            private String supermarketName;
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

            public String getGoodsSkuId() {
                return goodsSkuId;
            }

            public void setGoodsSkuId(String goodsSkuId) {
                this.goodsSkuId = goodsSkuId;
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

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }
        }
    }
}
