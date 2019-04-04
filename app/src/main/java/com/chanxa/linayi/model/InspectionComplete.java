package com.chanxa.linayi.model;


import com.chanxa.linayi.HttpClient.api.ApiResponse;

public class InspectionComplete extends ApiResponse<InspectionComplete> {

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
