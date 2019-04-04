package com.chanxa.linayi.model;

import java.util.List;

public class BinningBody {

    public List<ProcurementTaskListBean> getProcurementTaskList() {
        return procurementTaskList;
    }

    public void setProcurementTaskList(List<ProcurementTaskListBean> procurementTaskList) {
        this.procurementTaskList = procurementTaskList;
    }

    private List<ProcurementTaskListBean> procurementTaskList;

    public static class ProcurementTaskListBean {
        private String communityId;
        private String ordersId;
        private String procurementTaskId;
        private String actualQuantity;
        private String boxNo;//箱号，需要手动输入

        public String getCommunityId() {
            return communityId;
        }

        public void setCommunityId(String communityId) {
            this.communityId = communityId;
        }

        public String getOrdersId() {
            return ordersId;
        }

        public void setOrdersId(String ordersId) {
            this.ordersId = ordersId;
        }

        public String getProcurementTaskId() {
            return procurementTaskId;
        }

        public void setProcurementTaskId(String procurementTaskId) {
            this.procurementTaskId = procurementTaskId;
        }

        public String getActualQuantity() {
            return actualQuantity;
        }

        public void setActualQuantity(String actualQuantity) {
            this.actualQuantity = actualQuantity;
        }

        public String getBoxNo() {
            return boxNo;
        }

        public void setBoxNo(String boxNo) {
            this.boxNo = boxNo;
        }
    }
}
