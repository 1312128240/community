package com.chanxa.linayi.bean;


import com.chanxa.linayi.HttpClient.api.ApiResponse;

/**
 * Created by chanxa on 2018/6/21.
 */

public class AmountInfo extends ApiResponse<AmountInfo> {

    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
