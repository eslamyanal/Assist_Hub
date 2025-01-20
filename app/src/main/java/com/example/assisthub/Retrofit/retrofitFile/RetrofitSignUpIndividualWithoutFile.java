package com.example.assisthub.Retrofit.retrofitFile;

import com.example.assisthub.Retrofit.interfac.HTTP_Method;
import com.example.assisthub.Retrofit.interfac.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSignUpIndividualWithoutFile {


    private static RetrofitSignUpIndividualWithoutFile Retrofit = null ;
    private  HTTP_Method HTTP_Method ;

    private RetrofitSignUpIndividualWithoutFile() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL.SignupIndividualUserWithoutFile)
                .addConverterFactory(GsonConverterFactory.create()).build();
        HTTP_Method = retrofit .create (HTTP_Method .class);
    }

    public static synchronized RetrofitSignUpIndividualWithoutFile checkInstance() {
        if (Retrofit == null)
            Retrofit = new RetrofitSignUpIndividualWithoutFile();

        return Retrofit;
    }

    public HTTP_Method  getServicesInterface() {
        return HTTP_Method ;
    }














}
