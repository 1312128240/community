package com.chanxa.linayi.model;


import com.chanxa.linayi.HttpClient.api.ApiResponse;

public class Binning extends ApiResponse<Binning> {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
