package com.example.assisthub.Retrofit.retrofitFile;

import com.example.assisthub.Retrofit.interfac.HTTP_Method;
import com.example.assisthub.Retrofit.interfac.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class updateNewPasswordRetrofit {


    private static updateNewPasswordRetrofit Retrofit = null ;
    private HTTP_Method HTTP_Method ;

    private updateNewPasswordRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL.updateNewPassword)
                .addConverterFactory(GsonConverterFactory.create()).build();
        HTTP_Method = retrofit .create (HTTP_Method .class);
    }

    public static synchronized updateNewPasswordRetrofit checkInstance() {
        if ( Retrofit == null)
            Retrofit  = new updateNewPasswordRetrofit();

        return  Retrofit;
    }

    public HTTP_Method  getServicesInterface() {
        return HTTP_Method ;
    }









}
