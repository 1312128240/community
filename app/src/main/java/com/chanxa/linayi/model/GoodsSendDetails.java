package com.chanxa.linayi.model;



import com.chanxa.linayi.HttpClient.api.ApiResponse;

import java.util.List;

public class GoodsSendDetails extends ApiResponse<GoodsSendDetails> {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String address;
        private String createTime;
        private String mobile;
        private String ordersId;
        private String boxNo;
        private String quantity;
        private String receiverName;
        private List<GoodsSkuListBean> goodsSkuList;

        public String getBoxNo() {
            return boxNo;
        }

        public void setBoxNo(String boxNo) {
            this.boxNo = boxNo;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
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

        public List<GoodsSkuListBean> getGoodsSkuList() {
            return goodsSkuList;
        }

        public void setGoodsSkuList(List<GoodsSkuListBean> goodsSkuList) {
            this.goodsSkuList = goodsSkuList;
        }

        public static class GoodsSkuListBean {
            private String fullName;
            private String image;
            private String quantity;

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
            }

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
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
