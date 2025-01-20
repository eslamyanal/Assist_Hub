package com.example.assisthub.model;

import com.google.gson.annotations.SerializedName;

public class ApprovedBookingModel {




    @SerializedName("DateBooking")
    String DateBooking ;


    @SerializedName("hospital")
    String hospital ;

    @SerializedName("Section")
    String Section ;

    @SerializedName("Appointment_time")
    String Appointment_time ;

    @SerializedName("doctor")
    String doctor ;

    @SerializedName("bed")
    String bed;

    @SerializedName("period")
    String period;


    public ApprovedBookingModel(String dateBooking, String hospital, String section, String appointment_time, String doctor, String bed, String period) {
        DateBooking = dateBooking;
        this.hospital = hospital;
        Section = section;
        Appointment_time = appointment_time;
        this.doctor = doctor;
        this.bed = bed;
        this.period = period;
    }


    public String getDateBooking() {
        return DateBooking;
    }

    public String getHospital() {
        return hospital;
    }

    public String getSection() {
        return Section;
    }

    public String getAppointment_time() {
        return Appointment_time;
    }

    public String getDoctor() {
        return doctor;
    }

    public String getBed() {
        return bed;
    }

    public String getPeriod() {
        return period;
    }
}
