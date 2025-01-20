package com.example.assisthub.Retrofit.retrofitFile;

import com.example.assisthub.Retrofit.interfac.HTTP_Method;
import com.example.assisthub.Retrofit.interfac.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class updateUserDataRetrofit {



    private static updateUserDataRetrofit Retrofit = null ;
    private HTTP_Method HTTP_Method ;

    private updateUserDataRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL.UpdateUserData)
                .addConverterFactory(GsonConverterFactory.create()).build();
        HTTP_Method = retrofit .create (HTTP_Method .class);
    }

    public static synchronized updateUserDataRetrofit checkInstance() {
        if ( Retrofit == null)
            Retrofit  = new updateUserDataRetrofit();

        return  Retrofit;
    }

    public HTTP_Method  getServicesInterface() {
        return HTTP_Method ;
    }






}
