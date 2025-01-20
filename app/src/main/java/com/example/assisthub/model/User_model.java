package com.example.assisthub.model;
public class User_model {
   private int Id;

    private String Name_User , File_Name ,
            Phone , NationaltyOrPassportNumber,
            Nationality  , Health_Insurance, password_User;


    public User_model(int Id, String Name_User, String Phone, String Nationality, String password_User, String NationaltyOrPassportNumber, String Health_Insurance, String File_Name) {
        this.Id = Id;
        this.Name_User = Name_User;
        this.Phone = Phone;
        this.Nationality = Nationality;
        this.password_User = password_User;
        this.NationaltyOrPassportNumber = NationaltyOrPassportNumber;
        this.Health_Insurance = Health_Insurance;
        this.File_Name = File_Name;

    }



    public int getId() {
        return Id;
    }

    public String getName_User() {
        return Name_User;
    }

    public String getPhone() {
        return Phone;
    }

    public String getNationality() {
        return Nationality;
    }

    public String getPassword_User() {
        return password_User;
    }

    public String getNationaltyOrPassportNumber() {
        return NationaltyOrPassportNumber;
    }

    public String getHealth_Insurance() {
        return Health_Insurance;
    }

    public String getFile_Name() {
        return File_Name;
    }


}
