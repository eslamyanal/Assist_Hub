package com.example.assisthub.model;

import com.google.gson.annotations.SerializedName;

public class volunteer {


    @SerializedName("id")
    int id ;

    @SerializedName("name")
    String name;


    @SerializedName("image")
    String image;


    @SerializedName("note")
    String note ;


    @SerializedName("link")
    String link;

    public volunteer(int id, String name, String image, String note, String link) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.note = note;
        this.link = link;
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

    public String getNote() {
        return note;
    }

    public String getLink() {
        return link;
    }



}
