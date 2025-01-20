package com.example.assisthub.model;

import com.google.gson.annotations.SerializedName;

public class returnBookings {

    @SerializedName("id")
    int id ;



    @SerializedName("hospital")
    String hospital;


    @SerializedName("section")
    String section ;

    @SerializedName("period")
    String period;

    @SerializedName("state")
    String state ;


    @SerializedName("dateBed")
    String dateBed;

    @SerializedName("doctor")
    String doctor;

    @SerializedName("bed")
    String bed;


    @SerializedName("cost")
    int cost;

    public returnBookings( int id ,String hospital, String section, String period, String state, String dateBed, String doctor, String bed , int cost) {
        this.id = id;
        this.hospital = hospital;
        this.section = section;
        this.period = period;
        this.state = state;
        this.dateBed = dateBed;
        this.doctor = doctor;
        this.bed = bed;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }


    public String getHospital() {
        return hospital;
    }

    public String getSection() {
        return section;
    }

    public String getPeriod() {
        return period;
    }

    public String getState() {
        return state;
    }

    public String getDateBed() {
        return dateBed;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getBed() {
        return bed;
    }

    public int getCost() {
        return cost;
    }
}
