package com.example.assisthub.Retrofit.retrofitFile;

import com.example.assisthub.Retrofit.interfac.HTTP_Method;
import com.example.assisthub.Retrofit.interfac.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceRetrofit {


    private static ServiceRetrofit ServiceRetrofit = null ;
    private HTTP_Method HTTP_Method ;

    private ServiceRetrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL.Service)
                .addConverterFactory(GsonConverterFactory.create()).build();
        HTTP_Method = retrofit .create (HTTP_Method .class);
    }

    public static synchronized ServiceRetrofit checkInstance() {
        if ( ServiceRetrofit == null)
            ServiceRetrofit  = new ServiceRetrofit();

        return  ServiceRetrofit;
    }

    public HTTP_Method  getServicesInterface() {
        return HTTP_Method ;
    }




}
