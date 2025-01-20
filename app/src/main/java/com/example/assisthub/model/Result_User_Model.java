package com.example.assisthub.model;

import com.google.gson.annotations.SerializedName;

public class Result_User_Model {

    @SerializedName("error")
    boolean error ;

    @SerializedName("message")
    String message ;

    @SerializedName("User")
    User_model user ;


    public Result_User_Model(boolean error, String message, User_model user) {
        this.error = error;
        this.message = message;
        this.user = user;
    }

    public Result_User_Model(boolean error, String message) {
        this.error = error;
        this.message = message;

    }



    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public User_model getUser() {
        return user;
    }
}
