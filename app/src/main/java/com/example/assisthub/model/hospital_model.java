package com.example.assisthub.model;

import com.google.gson.annotations.SerializedName;

public class hospital_model {


    @SerializedName("id")
    int id;

    @SerializedName("name")
    String name ;

    @SerializedName("image")
    String image;

    @SerializedName("fk_id_reigon")
    int fk_id_reigon;

    @SerializedName("fk_id_type")
    int fk_id_type;

    public hospital_model(int id, String name, String image, int fk_id_reigon, int fk_id_type) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.fk_id_reigon = fk_id_reigon;
        this.fk_id_type = fk_id_type;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getFk_id_reigon() {
        return fk_id_reigon;
    }

    public int getFk_id_type() {
        return fk_id_type;
    }
}
