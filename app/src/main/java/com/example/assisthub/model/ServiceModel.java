package com.example.assisthub.model;

import com.google.gson.annotations.SerializedName;

public class ServiceModel {

    @SerializedName("idDoctor")
    int idDoctor;
    @SerializedName("nameDoctor")
    String nameDoctor ;
    @SerializedName("DoctorType")
    String DoctorType;
    @SerializedName("imageDoctor")
    String imageDoctor;

    @SerializedName("TimeAvailable")
    String TimeAvailable;
    @SerializedName("Day")
    String Day;

    @SerializedName("CostDoctor")
    int  costDoctor;



    @SerializedName("idBed")
    int idBed;
    @SerializedName("imageBed")
    String imageBed;
    @SerializedName("nameBed")
    String nameBed;


    @SerializedName("CostBed")
    int costBed;

    @SerializedName("hospital_fk_id")
    int hospital_fk_id;
    @SerializedName("Sections_fk_id")
    int Sections_fk_id;

    @SerializedName("IsFull")
    int IsFull ;



    public ServiceModel(int idDoctor, int costBed , int  costDoctor , String nameDoctor, String doctorType, String imageDoctor, int hospital_fk_id, int sections_fk_id, String timeAvailable, String day, int idBed, String imageBed, String nameBed , int isFull) {
        this.idDoctor = idDoctor;
        this.nameDoctor = nameDoctor;
        DoctorType = doctorType;
        this.imageDoctor = imageDoctor;
        this.hospital_fk_id = hospital_fk_id;
        Sections_fk_id = sections_fk_id;
        TimeAvailable = timeAvailable;
        Day = day;
        this.idBed = idBed;
        this.imageBed = imageBed;
        this.nameBed = nameBed;
        this.costDoctor = costDoctor;
        this.costBed = costBed;
        IsFull = isFull;
    }

    public int getIdDoctor() {
        return idDoctor;
    }

    public String getNameDoctor() {
        return nameDoctor;
    }

    public String getDoctorType() {
        return DoctorType;
    }

    public String getImageDoctor() {
        return imageDoctor;
    }

    public int getHospital_fk_id() {
        return hospital_fk_id;
    }

    public int getSections_fk_id() {
        return Sections_fk_id;
    }

    public String getTimeAvailable() {
        return TimeAvailable;
    }

    public String getDay() {
        return Day;
    }

    public int getIdBed() {
        return idBed;
    }

    public String getImageBed() {
        return imageBed;
    }

    public String getNameBed() {
        return nameBed;
    }


    public int getCostDoctor() {
        return costDoctor;
    }

    public int getCostBed() {
        return costBed;
    }

    public int getIsFull() {
        return IsFull;
    }
}
