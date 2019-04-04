package com.chanxa.linayi.model;


import com.chanxa.linayi.HttpClient.api.ApiResponse;

public class SealBox extends ApiResponse<SealBox> {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
