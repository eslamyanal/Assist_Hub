package com.example.assisthub.model;

import com.google.gson.annotations.SerializedName;

public class CheckModel {


    @SerializedName("error")
     Boolean error ;

    @SerializedName("message")
     String message;


    public CheckModel(Boolean error, String message) {
        this.error = error;
        this.message = message;
    }


    public Boolean geterror() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
