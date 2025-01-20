package com.example.assisthub.model;

import com.google.gson.annotations.SerializedName;

public class deleteRequestModel {

    @SerializedName("error")
    boolean error;

    @SerializedName("message")
    String message;

    @SerializedName("returnMoney")
    boolean returnMoney;


    public deleteRequestModel(boolean error, String message, boolean returnMoney) {
        this.error = error;
        this.message = message;
        this.returnMoney = returnMoney;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public boolean isReturnMoney() {
        return returnMoney;
    }
}
