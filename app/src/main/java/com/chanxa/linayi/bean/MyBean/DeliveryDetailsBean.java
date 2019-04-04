package com.chanxa.linayi.bean.MyBean;

import java.util.List;

/**
 * Created by Administrator on 2019/4/1.
 */

public class DeliveryDetailsBean {

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

        private String address;
        private String boxNo;
        private long createTime;
        private String mobile;
        private int ordersId;
        private int quantity;
        private String receiverName;
        private List<GoodsSkuListBean> goodsSkuList;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBoxNo() {
            return boxNo;
        }

        public void setBoxNo(String boxNo) {
            this.boxNo = boxNo;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
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

        public List<GoodsSkuListBean> getGoodsSkuList() {
            return goodsSkuList;
        }

        public void setGoodsSkuList(List<GoodsSkuListBean> goodsSkuList) {
            this.goodsSkuList = goodsSkuList;
        }

        public static class GoodsSkuListBean {

            private String fullName;
            private String image;
            private int quantity;

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

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }
        }
    }
}
