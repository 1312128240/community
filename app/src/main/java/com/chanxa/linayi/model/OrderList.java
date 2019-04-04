package com.chanxa.linayi.model;

import com.chanxa.linayi.HttpClient.api.ApiResponse;

import java.util.List;

public class OrderList extends ApiResponse<OrderList> {

    private DataBeanX data;

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        private String total;
        private String totalPage;
        private List<DataBean> data;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
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
            private String communityStatus;
            private String createDate;
            private String createDateStr;
            private String goodsTotalPrice;
            private String ordersId;
            private String payPrice;
            private String receiverName;
            private String status;
            private String totalPrice;
            private String userStatus;
            private List<ShoppingCarListBean> shoppingCarList;

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

            public String getGoodsTotalPrice() {
                return goodsTotalPrice;
            }

            public void setGoodsTotalPrice(String goodsTotalPrice) {
                this.goodsTotalPrice = goodsTotalPrice;
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

            public String getReceiverName() {
                return receiverName;
            }

            public void setReceiverName(String receiverName) {
                this.receiverName = receiverName;
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
}
