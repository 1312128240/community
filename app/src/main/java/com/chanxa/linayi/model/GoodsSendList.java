package com.chanxa.linayi.model;



import com.chanxa.linayi.HttpClient.api.ApiResponse;

import java.util.List;

public class GoodsSendList extends ApiResponse<GoodsSendList> {

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
            private String boxNo;
            private String boxTime;
            private String ordersId;
            private String quantity;
            private String receiverName;

            public String getBoxNo() {
                return boxNo;
            }

            public void setBoxNo(String boxNo) {
                this.boxNo = boxNo;
            }

            public String getBoxTime() {
                return boxTime;
            }

            public void setBoxTime(String boxTime) {
                this.boxTime = boxTime;
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
        }
    }
}
