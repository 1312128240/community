package com.chanxa.linayi.bean;

public class OrderLisBody {

    private String communityStatus;
    private int pageSize;
    private int currentPage;

    public String getCommunityStatus() {
        return communityStatus;
    }

    public void setCommunityStatus(String communityStatus) {
        this.communityStatus = communityStatus;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
