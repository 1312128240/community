package com.chanxa.linayi.bean.MyBean;

import java.util.List;

public class ReceivBean {


    private String errorMsg;
    private DataBeanX data;
    private String respCode;


    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
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

        private int totalPage;
        private List<DataBean> data;

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

            private long accessTime;
            private int actualQuantity;
            private String fullName;
            private String goodsImage;
            private int goodsSkuId;
            private int price;
            private int quantity;

            private String totalPrice;
            private String procurementTaskIdList;

            public String getProcurementTaskIdList() {
                return procurementTaskIdList;
            }

            public void setProcurementTaskIdList(String procurementTaskIdList) {
                this.procurementTaskIdList = procurementTaskIdList;
            }

            public long getAccessTime() {
                return accessTime;
            }

            public void setAccessTime(long accessTime) {
                this.accessTime = accessTime;
            }

            public int getActualQuantity() {
                return actualQuantity;
            }

            public void setActualQuantity(int actualQuantity) {
                this.actualQuantity = actualQuantity;
            }

            public String getFullName() {
                return fullName;
            }

            public void setFullName(String fullName) {
                this.fullName = fullName;
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

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public int getQuantity() {
                return quantity;
            }

            public void setQuantity(int quantity) {
                this.quantity = quantity;
            }

            public String getTotalPrice() {
                return totalPrice;
            }

            public void setTotalPrice(String totalPrice) {
                this.totalPrice = totalPrice;
            }
        }
    }
}
