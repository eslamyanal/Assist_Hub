package com.example.assisthub.model;

import com.google.gson.annotations.SerializedName;

public class RequestsRejectedModel {


    @SerializedName("Id")
    int Id ;

    @SerializedName("Hospital")
    String Hospital;

    @SerializedName("Section")
    String Section ;

    @SerializedName("message")
    String message;

    @SerializedName("refundAmount")
    int refundAmount;

    @SerializedName("doctor")
    String doctor ;

    @SerializedName("bed")
    String bed ;

    @SerializedName("IsReturnPayment")
    int IsReturnPayment ;


    public RequestsRejectedModel(int id, String hospital, String section, String message, int refundAmount, String doctor, String bed, int IsReturnPayment) {
        Id = id;
        Hospital = hospital;
        Section = section;
        this.message = message;
        this.refundAmount = refundAmount;
        this.doctor = doctor;
        this.bed = bed;
        this.IsReturnPayment = IsReturnPayment;
    }


    public int getId() {
        return Id;
    }

    public String getHospital() {
        return Hospital;
    }

    public String getSection() {
        return Section;
    }

    public String getMessage() {
        return message;
    }

    public int getRefundAmount() {
        return refundAmount;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getBed() {
        return bed;
    }

    public int IsReturnPayment() {
        return IsReturnPayment;
    }
}
